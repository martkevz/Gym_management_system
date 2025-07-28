package com.app.gym.modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class AsistenciaId implements Serializable{

    /*
     * Clase que representa una clave primaria compuesta para la entidad Asistencia.
     * Esta clase implementa Serializable para permitir su uso como clave primaria en JPA.
     */

    private Integer idAsistencia;
    private LocalDate fecha;

    public AsistenciaId() {
        // Constructor por defecto
    }

    /**
     * Constructor con parámetros para inicializar los campos de la clase.
     *
     * @param idAsistencia Identificador único de la asistencia.
     * @param fecha Fecha de la asistencia.
     */
    public AsistenciaId(Integer idAsistencia, LocalDate fecha) {
        this.idAsistencia = idAsistencia;
        this.fecha = fecha;
    }

    /*
     * Getters y setters
     */
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
    
    /**
     * Compara dos objetos VentaId para verificar si son iguales.
     *
     * @param o Objeto a comparar con la instancia actual.
     * @return true si ambos objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsistenciaId)) return false;
        AsistenciaId asistenciaId = (AsistenciaId) o;
        return idAsistencia.equals(asistenciaId.idAsistencia) && fecha.equals(asistenciaId.fecha);
    }

    /**
     * Genera un código hash para la clave compuesta.
     *
     * @return Código hash basado en los campos idVenta y fecha.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAsistencia, fecha);
    }

}
