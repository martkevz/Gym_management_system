package com.app.gym.excepciones;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorExcepcionesGlobal {

    /**
     * Maneja excepciones de recursos no encontrados.
     *
     * @param ex la excepción lanzada
     * @return ResponseEntity con el mensaje de error y estado correspondiente (not found, bad request, etc.)
     */

    @ExceptionHandler(RecursoNoEncontradoExcepcion.class)
    public ResponseEntity<String> handleRecursoNoEncontrado(RecursoNoEncontradoExcepcion ex) { // Maneja excepciones de recursos no encontrados
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(IllegalStateException.class) // Maneja excepciones de estado ilegal
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class) // Maneja excepciones generales
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + ex.getMessage());
    }

    @ExceptionHandler(FechaInvalidaExcepcion.class) // Maneja excepciones de fecha inválida
    public ResponseEntity<?> handleFechaInvalidaExcepcion(FechaInvalidaExcepcion ex){
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }   
}
