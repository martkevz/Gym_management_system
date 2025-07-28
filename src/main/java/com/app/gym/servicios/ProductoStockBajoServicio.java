package com.app.gym.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.gym.modelos.ProductoStockBajo;
import com.app.gym.repositorios.ProductoStockBajoRepositorio;

@Service
public class ProductoStockBajoServicio {
    /**
     * Servicio para gestionar productos con stock bajo.
     * Proporciona métodos para obtener productos cuyo stock es igual o menor a 10 unidades.
     */

    private final ProductoStockBajoRepositorio repositorio;

    /**
     * Constructor del servicio que inyecta el repositorio de productos con stock bajo.
     * 
     * @param repositorio Repositorio para acceder a los datos de productos con stock bajo.
     */
    public ProductoStockBajoServicio(ProductoStockBajoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Obtiene una lista de productos cuyo stock es igual o menor a 10 unidades.
     * 
     * @return Lista de productos con stock bajo.
     */
    public List<ProductoStockBajo> obtenerProductosStockBajo() {
        return repositorio.findAll();
    }
}
