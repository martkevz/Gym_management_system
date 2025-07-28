package com.app.gym.modelos;

import java.sql.Date;
import org.hibernate.annotations.Immutable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que mapea la vista SQL 'usuarios_activos'.
 * Representa usuarios con estado de membresía activo o vencido.
 * Esta clase es de solo lectura: Hibernate no realizará operaciones
 * de INSERT, UPDATE ni DELETE sobre la vista.
 */
@Entity
@Immutable
@Table(name = "usuarios_activos")
public class UsuarioActivo {

    /**
     * Identificador único del usuario.
     * Corresponde al campo 'id_usuario' de la vista.
     */
    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Apellido del usuario.
     */
    private String apellido;

    /**
     * Fecha de inicio de la membresía.
     * Corresponde al campo 'fecha_inicio_membresia'.
     */
    @Column(name = "fecha_inicio_membresia")
    private Date fechaInicioMembresia;

    /**
     * Fecha de fin de la membresía.
     * Corresponde al campo 'fecha_fin_membresia'.
     */
    @Column(name = "fecha_fin_membresia")
    private Date fechaFinMembresia;

    /**
     * Nombre del tipo de membresía asociada al usuario.
     */
    @Column(name = "tipo_membresia")
    private String tipoMembresia;

    /**
     * Duración de la membresía en días o meses, según definición en la DB.
     */
    private String duracion;

    /**
     * Indicador de si la membresía está activa.
     * true = vigente, false = vencida.
     */
    @Column(name = "membresia_activa")
    private Boolean membresiaActiva;

    /**
     * Obtiene el identificador del usuario.
     *
     * @return idUsuario
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     *
     * @return apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Obtiene la fecha de inicio de la membresía.
     *
     * @return fechaInicioMembresia
     */
    public Date getFechaInicioMembresia() {
        return fechaInicioMembresia;
    }

    /**
     * Obtiene la fecha de fin de la membresía.
     *
     * @return fechaFinMembresia
     */
    public Date getFechaFinMembresia() {
        return fechaFinMembresia;
    }

    /**
     * Obtiene el nombre del tipo de membresía.
     *
     * @return tipoMembresia
     */
    public String getTipoMembresia() {
        return tipoMembresia;
    }

    /**
     * Obtiene la duración de la membresía.
     *
     * @return duracion
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * Indica si la membresía está activa.
     *
     * @return true si vigente, false si vencida
     */
    public Boolean getMembresiaActiva() {
        return membresiaActiva;
    }
}