package com.app.gym.dtos;

import java.time.LocalTime;

public class AsistenciaGeneralActualizarDTO {

    /*
     * DTO para actualizar la asistencia general de un usuario a una clase aeróbica.
     * Contiene los campos necesarios para modificar la hora de entrada y el estado de anulación de la asistencia.
     * Este DTO se utiliza para realizar actualizaciones (updates) en la asistencia registrada.
     */

    private LocalTime horaEntrada;

    /** Marca la asistencia como anulada (soft-delete) que por defecto el false*/
    private boolean anulada;

    // Getters y Setters

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public boolean getAnulada() {
        return anulada;
    }

    public void setAnulada(boolean anulada) {
        this.anulada = anulada;
    }
}
