package com.app.gym.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO usado para actualizar una venta:
 * - cantidad: nueva cantidad de productos
 * - anulada: opcional, marca si la venta está anulada
 */
public class VentaActualizarDto {

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    /** Marca la venta como anulada (soft-delete) */
    private Boolean anulada;

    //Getters y Setters
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getAnulada() {
        return anulada;
    }

    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }
}
