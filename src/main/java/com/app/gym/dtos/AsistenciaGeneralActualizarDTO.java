package com.app.gym.dtos;

import java.time.LocalTime;

public class AsistenciaGeneralActualizarDTO {

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
