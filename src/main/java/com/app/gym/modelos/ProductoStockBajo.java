package com.app.gym.modelos;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Immutable
@Table(name = "productos_stock_bajo")
public class ProductoStockBajo {

    /**
    * Entidad de solo lectura que representa la vista SQL 'productos_stock_bajo'.
    * Esta vista identifica productos cuyo stock es igual o menor a 10 unidades,
    * mostrando además un porcentaje relativo del stock.
     */

    @Id
    @Column(name = "id_producto")
    private Integer idProducto;

    // Nombre del producto.
    private String nombre;
    
    // Cantidad de unidades disponibles del producto.
    @Column(name = "cantidad_disponible")
    private Integer cantidadDisponible;

    // Porcentaje del stock disponible respecto al total.
    @Column(name = "porcentaje_stock")
    private Double porcentajeStock;

    /**
     * Obtiene el identificador del producto.
     *
     * @return idProducto
     */
    public Integer getIdProducto() {
        return idProducto;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la cantidad de unidades disponibles del producto.
     *
     * @return cantidadDisponible
     */
    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    /**
     * Obtiene el porcentaje del stock disponible respecto al total.
     *
     * @return porcentajeStock
     */
    public Double getPorcentajeStock() {
        return porcentajeStock;
    }

    
}
