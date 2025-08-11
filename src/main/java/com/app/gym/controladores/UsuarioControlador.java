package com.app.gym.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.gym.dtos.usuario.UsuarioActualizarDTO;
import com.app.gym.dtos.usuario.UsuarioRequestDTO;
import com.app.gym.modelos.Usuario;
import com.app.gym.servicios.UsuarioServicio;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


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

    /*--------------------------------------------------------------
     * 2. Actualizar usuario. /api/usuarios/{id}
     *-------------------------------------------------------------*/
    
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioActualizarDTO dto, BindingResult br){
        
        if(br.hasErrors()){
            List<String> errores = br.getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).toList();
            
            return ResponseEntity.badRequest().body(Map.of("error", errores));
        } 
        
        Usuario usuario = usuarioServicio.actualizarUsuario(id, dto);
        return ResponseEntity.ok(usuarioServicio.toResponseDTO(usuario));
    }

    /*--------------------------------------------------------------
     * 3. Buscar usuario por ID. /api/usuarios/{id}
     *-------------------------------------------------------------*/
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioServicio.buscarPorId(id);
        return ResponseEntity.ok(usuarioServicio.toResponseDTO(usuario));
    }

    /*--------------------------------------------------------------
     * 4. Buscar usuario por email. /api/usuarios
     *-------------------------------------------------------------*/
    @GetMapping(params = "email")
    public ResponseEntity<?> buscarUsuarioPorEmail(@RequestParam String email) {
        Usuario usuario = usuarioServicio.buscarPorEmail(email);
        return ResponseEntity.ok(usuarioServicio.toResponseDTO(usuario));
    }

    /*--------------------------------------------------------------
     * 5. Buscar usuarios cuya membresía está por vencer en los próximos 7 días. /api/usuarios/por-vencer
     *-------------------------------------------------------------*/
    @GetMapping("/por-vencer")
    public ResponseEntity<?> buscarUsuariosPorVencerEnUnaSemana() {

        List<Usuario> usuarios = usuarioServicio.usuariosPorVencerEnUnaSemana();
        return ResponseEntity.ok(usuarioServicio.toResponseDTO(usuarios));
    }
}
