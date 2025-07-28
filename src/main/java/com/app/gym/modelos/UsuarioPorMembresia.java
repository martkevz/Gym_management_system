package com.app.gym.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "usuarios_por_membresia")
public class UsuarioPorMembresia {

    /**
    * Entidad de solo lectura que representa la vista SQL 'usuarios_por_membresia'.
    * Se utiliza para mostrar un resumen por tipo de membresía con cantidad de usuarios
    * y el monto total generado.
    */
    
    /**
     * Identificador de la membresía.
     * Corresponde al campo 'membresia' de la vista.
     */
    @Id
    @Column(name = "membresia")
    private String membresia;

    /**
     * Precio de la membresía.
     * Corresponde al campo 'precio' de la vista.
     */
    private Double precio;

    /**
     * Cantidad de usuarios asociados a esta membresía.
     * Corresponde al campo 'cantidad_usuarios' de la vista.
     */
    @Column(name = "cantidad_usuarios")
    private Integer cantidadUsuarios;

    /**
     * Monto total generado por esta membresía.
     * Corresponde al campo 'monto_total_generado' de la vista.
     */
    @Column(name = "monto_total_generado")
    private Double montoTotalGenerado;

    /**
     * Obtiene el identificador de la membresia.
     *
     * @return membresia
     */
    public String getMembresia() {
        return membresia;
    }

    public Double getPrecio() {
        return precio;
    }

    /**
     * Obtiene la cantidad de usuarios asociados a esta membresía.
     *
     * @return cantidad de usuarios
     */
    public Integer getCantidadUsuarios() {
        return cantidadUsuarios;
    }

    /**
     * Obtiene el monto total generado por esta membresía.
     *
     * @return monto total generado
     */
    public Double getMontoTotalGenerado() {
        return montoTotalGenerado;
    }
}
