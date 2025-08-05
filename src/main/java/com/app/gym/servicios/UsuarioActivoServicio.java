package com.app.gym.servicios;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.gym.modelos.UsuarioActivo;
import com.app.gym.repositorios.UsuarioActivoRepositorio;

/**
 * Servicio de negocio para operaciones sobre Usuarios Activos.
 * Encapsula la lógica de obtención de datos desde el repositorio.
 */
@Service
public class UsuarioActivoServicio {
    private final UsuarioActivoRepositorio repo;

    public UsuarioActivoServicio(UsuarioActivoRepositorio repo) {
        this.repo = repo;
    }

    /**
     * Recupera todos los registros de la vista Usuarios_Activos.
     *
     * @return lista completa de UsuarioActivo
     */
    @Transactional(readOnly = true)
    public List<UsuarioActivo> obtenerUsuarios() {
        return repo.findAll();
    }

    /**
     * Recupera sólo los usuarios con membresía activa.
     *
     * @return lista de usuarios activos
     */
    @Transactional(readOnly = true)
    public List<UsuarioActivo> obtenerUsuariosActivos() {
        return repo.findByMembresiaActivaTrue();
    }

    /**
     * Recupera sólo los usuarios con membresía vencida.
     *
     * @return lista de usuarios vencidos
     */
    @Transactional(readOnly = true)
    public List<UsuarioActivo> obtenerUsuariosVencidos() {
        return repo.findByMembresiaActivaFalse();
    }
}
