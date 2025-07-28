package com.app.gym.modelos;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(VentaId.class) // Indica que esta entidad utiliza una clave primaria compuesta
@Table(name = "ventas")
public class Venta {
    
    /*
     * Clase que representa una venta en el sistema de gestión de gimnasio.
     * Utiliza una clave primaria compuesta formada por el identificador de la venta y la
     * fecha de la venta.
     * Esta clase es una entidad JPA y se mapea a la tabla "ventas"
     */
    @Id  
    @Column(name = "id_venta", nullable = false) // Generamos el Id manualmente en la BD por ser una PK compuesta 
    private Integer idVenta; 

    /**
     * Identificador único de la venta.
     * Este campo es generado automáticamente por la base de datos.
     */
    @Id
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /**
     * Fecha de la venta.
     * Este campo es parte de la clave primaria compuesta junto con idVenta.
     */
    @Column(nullable = false)
    private Integer cantidad;

    /**
     * Cantidad de productos vendidos en esta venta.
     */
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal total;

    /* Indica si la venta está anulada (soft-delete)
     * Este campo es opcional y se utiliza para marcar una venta como anulada sin eliminarla de la base de datos.
     */
    @Column(nullable = false)
    public Boolean anulada = false; 

    //Relación con otras entidades: -----------------------------------------------------------------------------

    /*  
     * Esta relación indica que una venta está asociada a un usuario específico.
     * Utiliza @ManyToOne para establecer una relación muchos a uno con la entidad Usuario
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario; 
    
    /*
     * Esta relación indica que una venta está asociada a un producto específico.
     * Utiliza @ManyToOne para establecer una relación muchos a uno con la entidad Producto
     */
    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", nullable = false)
    private Producto producto; //ponemos el nombre del linker

    // Getters y Setters: --------------------------------------------------------------------------------------
    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Boolean getAnulada() {
        return anulada;
    }

    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }

    /* Método toString para facilitar la visualización de los datos
     * de Venta. Este método devuelve una representación en cadena de los
     * atributos de Venta.
     */
    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", fecha=" + fecha +
                ", cantidad=" + cantidad +
                ", total=" + total +
                ", usuario=" + usuario +
                ", producto=" + producto +
                '}';
    }
}
