package com.app.gym.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class AsistenciaGeneralResponseDTO {
    
    /*  
     * DTO que representa la respuesta de una asistencia general.
     * Contiene información sobre la asistencia, incluyendo el ID, fecha, hora de entrada,
     */

    private Integer idAsistencia;
    private LocalDate fecha;
    private LocalTime horaEntrada;
    private HorarioPorDiaSimpleDTO horario;
    private UsuarioSimpleDTO usuario;

    // Getters y Setters
    public Integer getIdAsistencia() {
        return idAsistencia;
    }
    public void setIdAsistencia(Integer idAsistencia) {
        this.idAsistencia = idAsistencia;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }
    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    public HorarioPorDiaSimpleDTO getHorario() {
        return horario;
    }
    public void setHorario(HorarioPorDiaSimpleDTO horario) {
        this.horario = horario;
    }
    public UsuarioSimpleDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioSimpleDTO usuario) {
        this.usuario = usuario;
    }
    
}
