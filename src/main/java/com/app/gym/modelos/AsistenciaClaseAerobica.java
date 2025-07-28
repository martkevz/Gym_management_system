package com.app.gym.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Asistencia_clases_aerobicas") // Nombre de la tabla en la base de datos
public class AsistenciaClaseAerobica {
    
    /*
     * Esta clase representa la asistencia a clases aeróbicas en el sistema de gestión de gimnasio.
     * Cada instancia de esta clase corresponde a una asistencia específica
     */

    // Atributo para almacenar el identificador único de la asistencia a la clase aeróbica.
    // Este campo es de tipo Integer y se generará automáticamente al insertar un nuevo registro en
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia_aerobica", nullable = false)
    private Integer idAsistenciaClaseAerobica;

    /*
     * Relación con la entidad ClaseAerobica.
     * Esta relación indica que una asistencia a una clase aeróbica está asociada a una
     * clase aeróbica específica.
     * Se utiliza @ManyToOne para establecer una relación muchos a uno con la entidad ClaseAerobica,
     * donde múltiples asistencias pueden estar asociadas a una sola clase aeróbica.
     */
    @ManyToOne
    @JoinColumn(name = "id_clase", referencedColumnName = "id_clase", nullable = false)
    private ClaseAerobica claseAerobica;

    /*
     * Relación con la entidad Usuario.
     * Esta relación indica que una asistencia a una clase aeróbica está asociada a un usuario específico.
     * Se utiliza @ManyToOne para establecer una relación muchos a uno con la entidad Usuario,
     * donde múltiples asistencias pueden estar asociadas a un solo usuario.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario;

    //Constructor vacío
    public AsistenciaClaseAerobica() {
    }

    // Getters y Setters

    public Integer getIdAsistenciaClaseAerobica() {
        return idAsistenciaClaseAerobica;
    }

    public void setIdAsistenciaClaseAerobica(Integer idAsistenciaClaseAerobica) {
        this.idAsistenciaClaseAerobica = idAsistenciaClaseAerobica;
    }

    public ClaseAerobica getClaseAerobica() {
        return claseAerobica;
    }

    public void setClaseAerobica(ClaseAerobica claseAerobica) {
        this.claseAerobica = claseAerobica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Método toString para representar la asistencia a clase aeróbica
    @Override
    public String toString() {
        return "AsistenciaClaseAerobica{" +
                "idAsistenciaClaseAerobica=" + idAsistenciaClaseAerobica +
                ", claseAerobica=" + claseAerobica +
                ", usuario=" + usuario +
                '}';
    }
}
