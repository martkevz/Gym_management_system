package com.app.gym.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.gym.servicios.UsuarioPorMembresiaServicio;

@RestController
@RequestMapping("/api/usuarios-membresias")
public class UsuarioPorMembresiaControlador {
    
    private final UsuarioPorMembresiaServicio usuarioPorMembresiaServicio;

    public UsuarioPorMembresiaControlador(UsuarioPorMembresiaServicio usuarioPorMembresiaServicio) {
        this.usuarioPorMembresiaServicio = usuarioPorMembresiaServicio;
    }

    /*--------------------------------------------------------------
     * 1. Listar usuarios por membresias. /api/usuarios-membresias
     *-------------------------------------------------------------*/
    @GetMapping
    public ResponseEntity<?> listarUsuariosPorMembresias (){
        return ResponseEntity.ok(usuarioPorMembresiaServicio.obtenerUsuariosPorMembresias());
    }
}