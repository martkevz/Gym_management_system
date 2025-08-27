package com.app.gym.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.gym.dtos.horarioPorDia.HorarioPorDiaActualizarDTO;
import com.app.gym.modelos.HorarioPorDia;
import com.app.gym.servicios.HorarioPorDiaServicio;
import com.app.gym.utils.ListUtils;

@RestController
@RequestMapping("/api/horarios-por-dia")
public class HorariosPorDiaControlador {
    
    private HorarioPorDiaServicio horarioPorDiaServicio;

    public HorariosPorDiaControlador(HorarioPorDiaServicio horarioPorDiaServicio) {
        this.horarioPorDiaServicio = horarioPorDiaServicio;
    }

    /*------------------------------------------------------------------------------------------------------------
     * 1. Actualiza un horario por día (horaApertura - horaCierre). /api/horarios-por-dia/{id}
     *-----------------------------------------------------------------------------------------------------------*/

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarHorarioPorDia(@PathVariable Integer id, @RequestBody HorarioPorDiaActualizarDTO dto) {
        HorarioPorDia horarioActualizado = horarioPorDiaServicio.actualizarHorarioPorDia(id, dto);
        return ResponseEntity.ok(horarioPorDiaServicio.toResponseDTO(horarioActualizado));
    }

    /*----------------------------------------------------------------------------
     * 2. Buscar Horario Por Dia por su ID.      /api/horarios-por-dia/{id}  
     *----------------------------------------------------------------------------*/

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerHorarioPorId(@PathVariable Integer id) {
        HorarioPorDia horario = horarioPorDiaServicio.obtenerHorarioPorId(id);
        return ResponseEntity.ok(horarioPorDiaServicio.toResponseDTO(horario));
    }

    /*----------------------------------------------------------------------------
     * 3. Listar todos los Horarios Por Dia.      /api/horarios-por-dia  
     *----------------------------------------------------------------------------*/
    @GetMapping
    public ResponseEntity<?> obtenerHorarios() {
        return ListUtils.okMappedList(horarioPorDiaServicio.obtenerHorarios(), horarioPorDiaServicio::toResponseDTO);
    }
}
