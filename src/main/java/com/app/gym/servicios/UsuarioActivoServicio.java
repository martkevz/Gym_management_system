package com.app.gym.servicios;

import java.util.List;
import org.springframework.stereotype.Service;
import com.app.gym.modelos.UsuarioActivo;
import com.app.gym.repositorios.UsuarioActivoRepositorio;

/**
 * Servicio de negocio para operaciones sobre Usuarios Activos.
 * Encapsula la lógica de obtención de datos desde el repositorio.
 */
@Service
public class UsuarioActivoServicio {
    private final UsuarioActivoRepositorio repo;

    /**
     * Constructor que inyecta el repositorio de Usuarios Activos.
     *
     * @param repo instancia de UsuarioActivoRepo
     */
    public UsuarioActivoServicio(UsuarioActivoRepositorio repo) {
        this.repo = repo;
    }

    /**
     * Recupera todos los registros de la vista Usuarios_Activos.
     *
     * @return lista completa de UsuarioActivo
     */
    public List<UsuarioActivo> todos() {
        return repo.findAll();
    }

    /**
     * Recupera sólo los usuarios con membresía activa.
     *
     * @return lista de usuarios activos
     */
    public List<UsuarioActivo> activos() {
        return repo.findByMembresiaActivaTrue();
    }

    /**
     * Recupera sólo los usuarios con membresía vencida.
     *
     * @return lista de usuarios vencidos
     */
    public List<UsuarioActivo> vencidos() {
        return repo.findByMembresiaActivaFalse();
    }
}
