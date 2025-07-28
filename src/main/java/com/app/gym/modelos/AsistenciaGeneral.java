package com.app.gym.modelos;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(AsistenciaId.class)
@Table(name = "asistencia_general")
public class AsistenciaGeneral {

    /* 
     * Clase que representa la entidad AsistenciaGeneral en la base de datos.
     * Esta clase utiliza una clave primaria compuesta definida por la clase AsistenciaId.
     */

    @Id
    @Column(name = "id_asistencia", nullable = false)
    private Integer idAsistencia; // Identificador único de la asistencia

    @Id
    @Column(nullable = false)
    private LocalDate fecha; // Fecha de la asistencia

    @Column(name = "hora_entrada")
    private LocalTime horaEntrada; // Hora de entrada del usuario

    /*
     * Relación con la entidad HorarioPorDia.
     * Esta relación indica que una asistencia general está asociada a un horario específico del día.
     * Se utiliza @ManyToOne para establecer una relación muchos a uno con la entidad HorarioPorDia.
     * La columna "id_horario" en la tabla asistencia_general hace referencia a la columna "id_horario" en la tabla horario_por_dia.
     * La relación es obligatoria, por lo que no puede ser nula (nullable = false).
     */
    @ManyToOne
    @JoinColumn(name = "id_horario", referencedColumnName = "id_horario", nullable = false)
    private HorarioPorDia horarioPorDia; // Relación con la entidad HorarioPorDia

    /*
     * Relación con la entidad Usuario.
     * Esta relación indica que una asistencia general está asociada a un usuario específico.
     * Se utiliza @ManyToOne para establecer una relación muchos a uno con la entidad Usuario.
     * La columna "id_usuario" en la tabla asistencia_general hace referencia a la columna "id_usuario" en la tabla usuarios.
     * La relación es obligatoria, por lo que no puede ser nula (nullable = false).
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario; // Relación con la entidad Usuario

    //Constructor por defecto para la clase AsistenciaGeneral.
    public AsistenciaGeneral() {
        
    }

    // Getter y Setters
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

    public HorarioPorDia getHorarioPorDia() {
        return horarioPorDia;
    }

    public void setHorarioPorDia(HorarioPorDia horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Método para mostrar la información del objeto AsistenciaGeneral como una cadena de texto.
    @Override
    public String toString() {
        return "AsistenciaGeneral{" +
                "idAsistencia=" + idAsistencia +
                ", fecha=" + fecha +
                ", horaEntrada=" + horaEntrada +
                ", horarioPorDia=" + horarioPorDia +
                ", usuario=" + usuario +
                '}';
    }

}
