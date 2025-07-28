package com.app.gym.modelos;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "horarios_por_dia") // Nombre de la tabla en la base de datos
public class HorarioPorDia {
    
    /*
     * Esta clase representa los horarios de apertura y cierre del gimnasio por día.
     * Cada instancia de esta clase corresponde a un día específico y contiene la hora de apertura
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario", nullable = false)
    private Integer idHorario;

    /* En este atributo se almacenará cada día de la semana. Por ejemplo:
     * "lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo".
     * Este campo es de tipo String para facilitar la representación del día.
     */
    private String dia;

    /* Atributos para almacenar la hora de apertura y cierre del gimnasio.
     * Se utilizan tipos LocalTime para representar las horas de manera precisa.
     */
    @Column(name = "hora_apertura")
    private LocalTime horaApertura;

    @Column(name = "hora_cierre")
    private LocalTime horaCierre;

    /* Relación con la entidad AsistenciaGeneral.
     * Esta relación indica que un horario por día puede tener múltiples asistencias generales asociadas.
     * Se utiliza CascadeType.ALL para que las operaciones de persistencia se propaguen a las entidades relacionadas.
     */
    @OneToMany(mappedBy = "horarioPorDia", cascade = CascadeType.ALL)
    private List<AsistenciaGeneral> asistenciaGeneral;

    /* Relación con la entidad ClasesAerobicas.
     * Esta relación indica que un horario por día puede tener múltiples clases aeróbicas asociadas.
     * Se utiliza CascadeType.ALL para que las operaciones de persistencia se propaguen a las entidades relacionadas.
     */
    @OneToMany(mappedBy = "horarioPorDia", cascade = CascadeType.ALL)
    private List<ClaseAerobica> claseAerobica;

    // Getters y Setters

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    public List<AsistenciaGeneral> getAsistenciaGeneral() {
        return asistenciaGeneral;
    }

    public void setAsistenciaGeneral(List<AsistenciaGeneral> asistenciaGeneral) {
        this.asistenciaGeneral = asistenciaGeneral;
    }

    public List<ClaseAerobica> getClaseAerobica() {
        return claseAerobica;
    }

    public void setClaseAerobica(List<ClaseAerobica> claseAerobica) {
        this.claseAerobica = claseAerobica;
    }

    // Método toString para facilitar la visualización de los datos 
    @Override
    public String toString() {
        return "HorarioPorDia{" +
                "idHorario=" + idHorario +
                ", dia='" + dia + '\'' +
                ", horaApertura=" + horaApertura +
                ", horaCierre=" + horaCierre +
                '}';
    }
    
}
