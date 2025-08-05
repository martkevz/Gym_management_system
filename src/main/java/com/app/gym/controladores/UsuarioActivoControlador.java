package com.app.gym.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.gym.modelos.UsuarioActivo;
import com.app.gym.servicios.UsuarioActivoServicio;

@RestController
@RequestMapping("/api/usuarios-activos")
public class UsuarioActivoControlador {
    private final UsuarioActivoServicio usuarioActivoServicio;

    public UsuarioActivoControlador(UsuarioActivoServicio usuarioActivoServicio) {
        this.usuarioActivoServicio = usuarioActivoServicio;
    }

    /**
     * /api/usuarios-activos
     * @return lista de todos los usuarios activos
     */
    @GetMapping
    public ResponseEntity<?> obtenerUsuarios() {

        List<UsuarioActivo> usuarios = usuarioActivoServicio.obtenerUsuarios();

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios activos registrados.");
        }

        return ResponseEntity.ok(usuarios);
    }

    /**
     * Endpoint GET /api/usuarios-activos/activos
     * @return lista de usuarios con membresía activa
     * si la lista está vacía, retorna un 404 Not Found
     * si hay usuarios activos, retorna un 200 OK con la lista de usuarios activos
     */
    @GetMapping("/activos")
    public ResponseEntity<?> obtenerUsuariosActivos() {

        List<UsuarioActivo> activos = usuarioActivoServicio.obtenerUsuariosActivos();

        if(activos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios con membresía activa.");        
        }

        return ResponseEntity.ok(activos);
    }

    /**
     * /api/usuarios-activos/vencidos
     * @return lista de usuarios con membresía vencida
     * si la lista está vacía, retorna un 404 Not Found
     * si hay usuarios vencidos, retorna un 200 OK con la lista
     */
    @GetMapping("/vencidos")
    public ResponseEntity<?> obtenerUsuariosVencidos() {

        List<UsuarioActivo> vencidos = usuarioActivoServicio.obtenerUsuariosVencidos();

        if (vencidos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios con membresía vencida.");
        }
        
        return ResponseEntity.ok(vencidos);
    }
}
