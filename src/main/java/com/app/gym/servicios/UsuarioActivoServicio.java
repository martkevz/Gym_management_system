package com.app.gym.servicios;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.gym.modelos.UsuarioActivo;
import com.app.gym.repositorios.UsuarioActivoRepositorio;
import com.app.gym.utils.ListUtils;

/**
 * Servicio de negocio para operaciones sobre Usuarios Activos.
 * Encapsula la lógica de obtención de datos desde el repositorio.
 */
@Service
public class UsuarioActivoServicio {
    private final UsuarioActivoRepositorio usuarioActivoRepositorio;

    public UsuarioActivoServicio(UsuarioActivoRepositorio usuarioActivoRepositorio) {
        this.usuarioActivoRepositorio = usuarioActivoRepositorio;
    }

    /**
     * Recupera todos los registros de la vista Usuarios_Activos.
     *
     * @return lista completa de UsuarioActivo
     */
    @Transactional(readOnly = true)
    public List<UsuarioActivo> obtenerUsuarios() {;
        return ListUtils.emptyIfNull(usuarioActivoRepositorio.findAll());
    }

    /**
     * Recupera sólo los usuarios con membresía activa.
     *
     * @return lista de usuarios activos
     */
    @Transactional(readOnly = true)
    public List<UsuarioActivo> obtenerUsuariosActivos() {
        return ListUtils.emptyIfNull(usuarioActivoRepositorio.findByMembresiaActivaTrue());
    }

    /**
     * Recupera sólo los usuarios con membresía vencida.
     *
     * @return lista de usuarios vencidos
     */
    @Transactional(readOnly = true)
    public List<UsuarioActivo> obtenerUsuariosVencidos() {
        return ListUtils.emptyIfNull(usuarioActivoRepositorio.findByMembresiaActivaFalse());
    }
}
