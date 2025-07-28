package com.app.gym.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.gym.repositorios.UsuarioPorMembresiaRepositorio;
import com.app.gym.modelos.UsuarioPorMembresia;

/**
 * Servicio de negocio para operaciones sobre Usuarios por cada Membresia.
 * Encapsula la lógica de obtención de datos desde el repositorio.
 */

@Service
public class UsuarioPorMembresiaServicio {
    
    private final UsuarioPorMembresiaRepositorio repo;

    /**
     * Constructor que inyecta el repositorio de Usuarios por Membresia.
     *
     * @param repositorio instancia de UsuarioPorMembresiaRepositorio
     */
    public UsuarioPorMembresiaServicio(UsuarioPorMembresiaRepositorio repo) {
        this.repo = repo;
    }

    /**
     * Recupera todos los registros de la vista Usuarios_Por_Membresia.
     *
     * @return lista completa de UsuarioPorMembresia
     */
    public List<UsuarioPorMembresia> obtenerUsuariosPorMembresias() {
        return repo.findAll();
    }
}
