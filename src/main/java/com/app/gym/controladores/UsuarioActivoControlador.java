package com.app.gym.controladores;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.gym.modelos.UsuarioActivo;
import com.app.gym.servicios.UsuarioActivoServicio;

/**
 * Controlador REST para exponer endpoints relacionados con Usuarios Activos.
 * Define rutas para listar todos, activos y vencidos.
 */
@RestController
@RequestMapping("/api/usuarios-activos")
public class UsuarioActivoControlador {
    private final UsuarioActivoServicio svc;

    /**
     * Constructor que inyecta el servicio de Usuarios Activos.
     *
     * @param svc instancia de UsuarioActivoService
     */
    public UsuarioActivoControlador(UsuarioActivoServicio svc) {
        this.svc = svc;
    }

    /**
     * Endpoint GET /api/usuarios-activos
     *
     * @return lista de todos los usuarios (activos y vencidos)
     */
    @GetMapping
    public List<UsuarioActivo> listarTodos() {
        return svc.todos();
    }

    /**
     * Endpoint GET /api/usuarios-activos/activos
     *
     * @return lista de usuarios con membresía activa
     * si la lista está vacía, retorna un 404 Not Found
     * si hay usuarios activos, retorna un 200 OK con la lista de usuarios activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<UsuarioActivo>> listarActivos() {

        List<UsuarioActivo> activos = svc.activos();

        if(activos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        
        }

        return ResponseEntity.ok(activos);
    }

    /**
     * Endpoint GET /api/usuarios-activos/vencidos
     *
     * @return lista de usuarios con membresía vencida
     * si la lista está vacía, retorna un 404 Not Found
     * si hay usuarios vencidos, retorna un 200 OK con la lista
     */
    @GetMapping("/vencidos")
    public ResponseEntity<List<UsuarioActivo>> listarVencidos() {

        List<UsuarioActivo> vencidos = svc.vencidos();

        if (vencidos.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(vencidos);
    }
}
