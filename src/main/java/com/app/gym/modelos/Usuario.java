package com.app.gym.modelos;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios") // Nombre de la tabla en la base de datos
public class Usuario {

    /*
     * Esta clase representa a un usuario del sistema de gestión de gimnasio.
     * Cada instancia de esta clase corresponde a un usuario específico y contiene
     * información relevante como nombre, apellido, fecha de nacimiento, email,
     * fecha de inicio y fin de membresía.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;
    
    private String nombre;

    private String apellido;
    
    // Atributo para almacenar la fecha de nacimiento del usuario. En postgresql, será de tipo DATE.
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "email", unique = true) // Aseguramos que el email sea único en la base de datos
    private String email;

    // Atributo para almacenar la fecha de inicio de la membresía del usuario.
    // En postgresql, será de tipo DATE.
    @Column(name = "fecha_inicio_membresia", nullable = false)
    private Date fechaInicioMembresia;

    @Column(name = "fecha_fin_membresia", nullable = false)
    private Date fechaFinMembresia;

    /*
     * Relación con la entidad Membresia.
     * Esta relación indica que un usuario puede tener una membresía asociada.
     * Se utiliza @OneToOne para establecer una relación uno a uno con la entidad
     * Membresia, donde cada usuario puede tener una membresía específica.
     */
    @OneToOne
    @JoinColumn(name = "id_membresia", referencedColumnName = "id_membresia", nullable = false)
    private Membresia membresia;

    /*
     * Relación con la entidad Venta.
     * Esta relación indica que un usuario puede tener múltiples ventas asociadas.
     * Se utiliza @OneToMany para establecer una relación uno a muchos con la entidad
     * venta, donde cada usuario puede tener varias ventas registradas.
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Venta> venta;

    /*
     * Relación con la entidad Asistencia General.
     * Esta relación indica que un usuario puede tener múltiples asistencias
     * registradas. Se utiliza @OneToMany para establecer una relación uno a muchos
     * con la entidad asistencia_general, donde cada usuario puede tener varias asistencias
     * asociadas.
     */
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<AsistenciaGeneral> asistenciaGeneral;

    /*
     * Constructor vacío requerido por JPA.
     * Este constructor es necesario para que JPA pueda crear instancias de la
     * entidad Usuario al recuperar datos de la base de datos.
     */
    public Usuario() {
    }

    // Getters y Setters
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaInicioMembresia() {
        return fechaInicioMembresia;
    }

    public void setFechaInicioMembresia(Date fechaInicioMembresia) {
        this.fechaInicioMembresia = fechaInicioMembresia;
    }

    public Date getFechaFinMembresia() {
        return fechaFinMembresia;
    }

    public void setFechaFinMembresia(Date fechaFinMembresia) {
        this.fechaFinMembresia = fechaFinMembresia;
    }

    public Membresia getMembresia() {
        return membresia;
    }

    public void setMembresia(Membresia membresia) {
        this.membresia = membresia;
    }

    public List<Venta> getVenta() {
        return venta;
    }

    public void setVenta(List<Venta> venta) {
        this.venta = venta;
    }

    public List<AsistenciaGeneral> getAsistenciaGeneral() {
        return asistenciaGeneral;
    }

    public void setAsistenciaGeneral(List<AsistenciaGeneral> asistenciaGeneral) {
        this.asistenciaGeneral = asistenciaGeneral;
    }

    /* Método toString para facilitar la visualización de los datos
     * del usuario. Este método devuelve una representación en cadena de los
     * atributos del usuario, incluyendo el nombre de la membresía si está
     * asociada.
     */
    @Override
    public String toString() {
        return "Usuario{" +
            "idUsuario=" + idUsuario +
            ", nombre='" + nombre + '\'' +
            ", apellido='" + apellido + '\'' +
            ", email='" + email + '\'' +
            ", fechaNacimiento=" + fechaNacimiento +
            ", fechaInicioMembresia=" + fechaInicioMembresia +
            ", fechaFinMembresia=" + fechaFinMembresia +
            ", membresia=" + (membresia != null ? membresia.getNombre() : "Sin membresía") + 
            '}';
}
}