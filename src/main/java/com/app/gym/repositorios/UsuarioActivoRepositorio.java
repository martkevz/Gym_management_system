package com.app.gym.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.gym.modelos.UsuarioActivo;

/**
 * Repositorio JPA para la entidad de sólo lectura UsuarioActivo.
 * Proporciona acceso a la vista SQL 'usuarios_activos'.
 */
public interface UsuarioActivoRepositorio extends JpaRepository<UsuarioActivo, Integer> {

    /**
     * Obtiene todos los usuarios cuya membresía está vigente.
     *
     * @return lista de UsuarioActivo con membresiaActiva = true
     */
    List<UsuarioActivo> findByMembresiaActivaTrue();

    /**
     * Obtiene todos los usuarios cuya membresía está vencida.
     *
     * @return lista de UsuarioActivo con membresiaActiva = false
     */
    List<UsuarioActivo> findByMembresiaActivaFalse();
}

