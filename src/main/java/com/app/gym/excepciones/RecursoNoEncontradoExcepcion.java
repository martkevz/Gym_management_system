package com.app.gym.excepciones;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNoEncontradoExcepcion extends RuntimeException {
    
    /**
     * Excepción personalizada para manejar recursos no encontrados.
     * Se lanza cuando se intenta acceder a un recurso que no existe en la base de datos.
     *
     * @param mensaje Mensaje descriptivo del error.
     */
    public RecursoNoEncontradoExcepcion(String mensaje) {
        super(mensaje);
    }
    
}
