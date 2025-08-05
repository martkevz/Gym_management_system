package com.app.gym.validadores;

import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;

import com.app.gym.excepciones.FechaInvalidaExcepcion;

public class FechaValidador {
    /**
     * Valida que la fecha proporcionada esté dentro del rango permitido.
     * La fecha debe ser posterior a la fecha actual y no anterior al año 2025.
     *
     * @param fecha la fecha a validar
     * @throws FechaInvalidaExcepcion si la fecha es inválida
     */
    
    public static void validarFecha(LocalDate fecha) {
        LocalDate fechaActual = LocalDate.now();
        
        if (fecha.isAfter(fechaActual) || fecha.getYear() < 2025) {
            throw new FechaInvalidaExcepcion("Fecha inválida: fuera del rango permitido");
        }
    }

    /**
     * Valida que el rango de fechas proporcionado sea correcto.
     * La fecha de inicio no puede ser posterior a la fecha de fin.
     *
     * @param inicio la fecha de inicio del rango
     * @param fin la fecha de fin del rango
     * @throws FechaInvalidaExcepcion si el rango es inválido
     */
    public static void validarRangoFechas(LocalDate inicio, LocalDate fin) {

        if (inicio.isAfter(fin)) {
            throw new FechaInvalidaExcepcion("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        
        validarFecha(inicio);
        validarFecha(fin);
    }

    /**
     * Valida que el año y mes proporcionados sean válidos.
     * El año debe ser mayor o igual a 2025 y el mes debe estar entre 1 y 12.
     * @param anio el año a validar
     * @param mes el mes a validar
     * @throws FechaInvalidaExcepcion si el año o mes son inválidos
     */
    public static void validarYearMonth (int anio, int mes){

        try{
            YearMonth fecha = YearMonth.of(anio, mes);

            if(fecha.isAfter(YearMonth.now()) || anio < 2025){
                throw new FechaInvalidaExcepcion("Fecha inválida: fuera del rango permitido");
            }
        }catch (DateTimeException e){
            throw new FechaInvalidaExcepcion("Mes o año no válido.");
        }
    }

    /**
     * Valida que el usuario tenga una membresía activa.
     * Si la fecha de fin de la membresía es nula o anterior a la fecha actual, se lanza una excepción.
     *
     * @param fechaFinMembresia la fecha de fin de la membresía del usuario
     * @throws IllegalStateException si la membresía no está activa
     */
    public static void validarFechaFinMembresia(Date fechaFinMembresia){

        if (fechaFinMembresia == null || fechaFinMembresia.toLocalDate().isBefore(LocalDate.now())){
            throw new IllegalStateException("El usuario no tiene una membresía activa");
        }
    }
}
