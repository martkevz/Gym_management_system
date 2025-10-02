package com.app.gym.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.gym.dtos.membresia.MembresiaActualizarDTO;
import com.app.gym.dtos.membresia.MembresiaRequestDTO;
import com.app.gym.servicios.MembresiaServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/membresias")
public class MembresiaControlador {

    MembresiaServicio membresiaServicio;

    public MembresiaControlador(MembresiaServicio membresiaServicio){
        this.membresiaServicio = membresiaServicio;
    }   

    /*--------------------------------------------------------------
     * 1. Registrar membresia. 
     *-------------------------------------------------------------*/

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMembresia(@Valid @RequestBody MembresiaRequestDTO dto, BindingResult br){ 

        if(br.hasErrors()) {
            List<String> errores = br.getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(Map.of("error", errores));
        }

        return ResponseEntity.ok(membresiaServicio.toResponseDTO(membresiaServicio.registrarMembresia(dto)));
    }

    /*--------------------------------------------------------------
     * 2. Actualizar membresia.
     *-------------------------------------------------------------*/

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarMembresia(@PathVariable Integer id, @Valid @RequestBody MembresiaActualizarDTO dto, BindingResult br) {

        if(br.hasErrors()) {
            List<String> errores = br.getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(Map.of("error", errores));
        }

        return ResponseEntity.ok(membresiaServicio.toResponseDTO(membresiaServicio.actualizarMembresia(id, dto)));
    } 
    
    /*--------------------------------------------------------------
     * 3. Obtener membresia por ID.
     *-------------------------------------------------------------*/

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMembresiaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(membresiaServicio.toResponseDTO(membresiaServicio.obtenerMembresiaPorId(id)));
    }

    /*--------------------------------------------------------------
     * 4. Obtener todas las membresias.
     *-------------------------------------------------------------*/
    
    @GetMapping
    public ResponseEntity<?> obtenerMembresias() {;
        return ResponseEntity.ok(membresiaServicio.toResponseDTO(membresiaServicio.obtenerMembresias()));
    }
}