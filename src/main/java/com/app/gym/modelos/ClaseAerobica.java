package com.app.gym.modelos;

import jakarta.persistence.GeneratedValue;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clases_aerobicas") // Nombre de la tabla en la base de datos
public class ClaseAerobica {
    
    /*
     * Clase que representa una clase aeróbica en el sistema de gestión de gimnasio.
     * Contiene información sobre la clase, como su ID. Ej: Clase con capacidad para 20 personas el lunes 15/03/2022 de 08:00 a 09:00 am.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID de la clase
    @Column(name = "id_clase", nullable = false)
    private Integer idClase; // ID de la clase aeróbica

    // Capacidad de la clase, es decir, cuántas personas pueden asistir a la clase
    @Column(name = "capacidad")
    private Integer capacidad;

    // Hora de inicio y fin de la clase
    // Utilizamos LocalTime para representar la hora sin fecha
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    //Relación con otras entidades: -----------------------------------------------------------------------------
    /*
     * Esta relación indica que una clase aeróbica está asociada a un horario específico.
     * Utiliza @ManyToOne para establecer una relación muchos a uno con la entidad HorarioPorDia
     */
    @ManyToOne
    @JoinColumn(name = "id_horario", referencedColumnName = "id_horario", nullable = false) // name (nombre para la columna), referencedColumnName (es la columna que hago referencia de la otra tabla).
    private HorarioPorDia horarioPorDia; // objeto de la tabla a la que damos la pk (El linker)

    @OneToMany(mappedBy = "claseAerobica") // "claseAeroica" es el nombre del linker en la entidad AsistenciaClaseAerobica
    private List<AsistenciaClaseAerobica> asistenciaClaseAerobica; // objeto de la tabla a la que damos la pk
                                                                   //@OneToMany → requiere una colección (List, Set, etc.)

    // Constructor vacío (requerido por JPA)
    public ClaseAerobica() {

    }

    // Getters y Setters
    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
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

    public HorarioPorDia getHorarioPorDia() {
        return horarioPorDia;
    }

    public void setHorarioPorDia(HorarioPorDia horarioPorDia) {
        this.horarioPorDia = horarioPorDia;
    }

    public List<AsistenciaClaseAerobica> getAsistenciaClaseAerobica() {
        return asistenciaClaseAerobica;
    }

    public void setAsistenciaClaseAerobica(List<AsistenciaClaseAerobica> asistenciaClaseAerobica) {
        this.asistenciaClaseAerobica = asistenciaClaseAerobica;
    }


    // Método toString para facilitar la visualización de los datos 
    @Override
    public String toString() {
        return "ClaseAerobica{" +
                "idClase=" + idClase +
                ", capacidad=" + capacidad +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", horarioPorDia=" + horarioPorDia +
                '}';
    }
}
