package com.app.gym.excepciones;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;

@RestControllerAdvice
public class ManejadorExcepcionesGlobal {

    /**
     * Clase para manejar excepciones globalmente en la aplicación.
     * Utiliza @RestControllerAdvice para interceptar excepciones lanzadas por los controladores
     */

    private static final Logger logger = LoggerFactory.getLogger(ManejadorExcepcionesGlobal.class);

    @ExceptionHandler(RecursoNoEncontradoExcepcion.class) // Maneja excepciones de recursos no encontrados
    public ResponseEntity<String> handleRecursoNoEncontrado(RecursoNoEncontradoExcepcion ex) { 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class) // Maneja excepciones de estado ilegal
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FechaInvalidaExcepcion.class) // Maneja excepciones de fecha inválida
    public ResponseEntity<?> handleFechaInvalidaExcepcion(FechaInvalidaExcepcion ex){
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }

    /**
     * Un único handler para violaciones de integridad (constraints únicos, etc.).
     * Acepta DataIntegrityViolationException y algunos tipos de root causes comunes.
     */
    @ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, PSQLException.class })
    public ResponseEntity<?> handleDataIntegrityViolation(Exception ex) {
        // Log para diagnóstico (no enviar excepciones crudas al cliente)
        logger.error("Violación de integridad detectada", ex);

        // Intentar obtener mensaje raíz que viene de la DB
        Throwable root = findRootCause(ex);
        String dbMessage = root != null && root.getMessage() != null ? root.getMessage() : ex.getMessage();
        String mensajeUsuario = "Violación de integridad de datos.";

        // Detectar casos comunes: llave duplicada / unique constraint
        if (dbMessage != null) {
            String lower = dbMessage.toLowerCase();
            if (lower.contains("duplicate") || lower.contains("duplicate key") || lower.contains("llave duplicada")
                    || lower.contains("unique") || lower.contains("violates unique") || lower.contains("already exists")) {
                // Intentar extraer columna/constraint del mensaje
                String encontrado = extractConstraintDetail(dbMessage);
                if (encontrado != null) {
                    mensajeUsuario = "Valor duplicado para: " + encontrado + ". Verifique y vuelva a intentar.";
                } else {
                    mensajeUsuario = "Ya existe un registro con ese valor único. Verifique e intente con otro.";
                }
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", mensajeUsuario));
            }
        }

        // Fallback: Bad request con mensaje genérico
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", mensajeUsuario));
    }

    // Catch-all: maneja cualquier excepción no prevista.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        logger.error("Error inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error inesperado: " + ex.getMessage()));
    }

    // ---------- helpers ----------

    private Throwable findRootCause(Throwable t) {
        Throwable root = t;
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }
        return root;
    }

    /**
     * Intenta parsear el nombre de columna/constraint desde el mensaje del motor (Postgres, Hibernate, etc.)
     */
    private String extractConstraintDetail(String dbMessage) {
        // Ejemplo Postgres: "Key (email)=(foo@bar.com) already exists."
        Pattern p1 = Pattern.compile("\\(([^)]+)\\)=\\(([^)]+)\\)");
        Matcher m1 = p1.matcher(dbMessage);
        if (m1.find()) {
            return m1.group(1) + " = " + m1.group(2);
        }
        // Ejemplo: constraint "usuarios_email_key"
        Pattern p2 = Pattern.compile("constraint \"([^\"]+)\"", Pattern.CASE_INSENSITIVE);
        Matcher m2 = p2.matcher(dbMessage);
        if (m2.find()) {
            return m2.group(1);
        }
        return null;
    }
}

