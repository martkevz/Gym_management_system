package com.app.gym.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.gym.modelos.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
    
}
