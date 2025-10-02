package com.app.gym.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.gym.servicios.UsuarioActivoServicio;

@RestController
@RequestMapping("/api/usuarios-activos")
public class UsuarioActivoControlador {
    private final UsuarioActivoServicio usuarioActivoServicio;

    public UsuarioActivoControlador(UsuarioActivoServicio usuarioActivoServicio) {
        this.usuarioActivoServicio = usuarioActivoServicio;
    }

    /*-------------------------------------------------------------------------------------
	 * 1. Obtener todos los usuarios (api/usuarios-activos)
	 *------------------------------------------------------------------------------------*/
    @GetMapping
    public ResponseEntity<?> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioActivoServicio.obtenerUsuarios());
    }

    /*-------------------------------------------------------------------------------------
	 * 2. Obtener usuarios con membresía activa (api/usuarios-activos/activos)
	 *------------------------------------------------------------------------------------*/
    @GetMapping("/activos")
    public ResponseEntity<?> obtenerUsuariosActivos() {
        return ResponseEntity.ok(usuarioActivoServicio.obtenerUsuariosActivos());
    }

    /*-------------------------------------------------------------------------------------
	 * 3. Obtener usuarios con membresía vencida (api/usuarios-activos/vencidos)
	 *------------------------------------------------------------------------------------*/
    @GetMapping("/vencidos")
    public ResponseEntity<?> obtenerUsuariosVencidos() {
        return ResponseEntity.ok(usuarioActivoServicio.obtenerUsuariosVencidos());
    }
}
