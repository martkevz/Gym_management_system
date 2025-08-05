package com.app.gym.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.gym.modelos.UsuarioPorMembresia;
import com.app.gym.servicios.UsuarioPorMembresiaServicio;

@RestController
@RequestMapping("/api/usuarios-membresias")
public class UsuarioPorMembresiaControlador {
    
    private final UsuarioPorMembresiaServicio servicio;

    public UsuarioPorMembresiaControlador(UsuarioPorMembresiaServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * /api/usuarios-membresias
     * @return resumen de usuarios agrupado por membresía
     * Si la lista está vacía, retorna un 404 Not Found.
     * Si hay usuarios, retorna un 200 OK con la lista de usuarios por membresia.
     */
    @GetMapping
    public ResponseEntity<?> listarUsuariosPorMembresias (){
        List<UsuarioPorMembresia> upm = servicio.obtenerUsuariosPorMembresias();

        if(upm.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron usuarios con membresías registradas.");
        }
        return ResponseEntity.ok(upm);
    }
}