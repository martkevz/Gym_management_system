package com.app.gym.excepciones;

public class FechaInvalidaExcepcion extends RuntimeException {
    
    /**
     * Excepción que se lanza cuando una fecha es inválida.
     * Esta excepción se utiliza para indicar que la fecha proporcionada
     * no cumple con los criterios de validez establecidos.
     */
    
    public FechaInvalidaExcepcion(String message) {
        super(message);
    }
}
