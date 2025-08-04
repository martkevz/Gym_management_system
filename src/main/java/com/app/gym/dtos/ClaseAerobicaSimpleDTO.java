package com.app.gym.dtos;

import java.time.LocalTime;

public class ClaseAerobicaSimpleDTO {
    
    private Integer idClaseAerobica;
    LocalTime horaInicio;
    LocalTime horaFin;
    HorarioPorDiaSimpleDTO horarioPorDia;

    // Getters y Setters
    public Integer getIdClaseAerobica() {
        return idClaseAerobica;
    }
    public void setIdClaseAerobica(Integer idClaseAerobica) {
        this.idClaseAerobica = idClaseAerobica;
    }
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    public LocalTime getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
    public HorarioPorDiaSimpleDTO getHorarioPorDia() {
        return horarioPorDia;
    }
    public void setHorarioPorDia(HorarioPorDiaSimpleDTO horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
    }
}
