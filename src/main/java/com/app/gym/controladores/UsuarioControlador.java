package com.app.gym.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.gym.dtos.usuario.UsuarioRequestDTO;
import com.app.gym.modelos.Usuario;
import com.app.gym.servicios.UsuarioServicio;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/usuarios")
public class UsuarioControlador {
    
    UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    /*--------------------------------------------------------------
     * 1. Registrar usuario. /api/usuarios/registrar
     *-------------------------------------------------------------*/

    /**
     * Registra un nuevo usuario en el sistema.
     * @param dto los datos del usuario a registrar
     * @param br  el resultado de la validación
     * @return el usuario registrado o un error si hay problemas de validación
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO dto, BindingResult br) {

        if(br.hasErrors()) {
            List<String> errores = br.getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(Map.of("error", errores));
        }
        
        Usuario usuario = usuarioServicio.registrarUsuario(dto);
        return ResponseEntity.ok(usuarioServicio.toResponseDTO(usuario));
    }

}
