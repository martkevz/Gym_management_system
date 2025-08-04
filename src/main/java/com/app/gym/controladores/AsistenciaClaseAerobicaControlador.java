package com.app.gym.controladores;

import java.util.Map;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.gym.dtos.AsistenciaClaseAerobicaActualizarDTO;
import com.app.gym.dtos.AsistenciaClaseAerobicaRequestDTO;
import com.app.gym.modelos.AsistenciaClaseAerobica;
import com.app.gym.servicios.AsistenciaClaseAerobicaServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/asistencia-clase-aerobica")
public class AsistenciaClaseAerobicaControlador {
    
    private final AsistenciaClaseAerobicaServicio asistenciaClaseAerobicaServicio;

    public AsistenciaClaseAerobicaControlador(AsistenciaClaseAerobicaServicio asistenciaClaseAerobicaServicio) {
        this.asistenciaClaseAerobicaServicio = asistenciaClaseAerobicaServicio;
    }
    
    /**
     * Registra la asistencia de un usuario a una clase aeróbica.
     *
     * @param dto DTO con los datos de la asistencia
     * @param br BindingResult para manejar errores de validación
     * @return ResponseEntity con el estado y el DTO de respuesta
     */
    /*-------------------------------------------------------------------------------------
	 * 1. Registrar asistencia clase aerobica (api/asistencia-clase-aerobica/registrar)
	 *------------------------------------------------------------------------------------*/
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarAsistenciaClaseAerobica(@Valid @RequestBody AsistenciaClaseAerobicaRequestDTO dto, BindingResult br){ //@valid valida las notaciones (como @NotNull) del DTO
	
		// Verifica si hay errores de validación en el objeto 'br'
		if(br.hasErrors()){
				// Si hay errores, los recoge y los convierte en una lista de cadenas formateadas mediante "stream()"
				List<String> errores = br.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage()) // Mapea cada error a una cadena con el formato "campo: mensaje de error"
                    .toList(); // Convierte el stream en una lista
					
				return ResponseEntity.badRequest().body(Map.of("errores", errores));
		}
		
        AsistenciaClaseAerobica asistencia = asistenciaClaseAerobicaServicio.registrarAsistenciaClaseAerobica(dto);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(asistenciaClaseAerobicaServicio.toResponseDTO(asistencia));
    }	
    /*-------------------------------------------------------------------------------------
	 * 2. Actualizar asistencia clase aerobica (api/asistencia-clase-aerobica/{id})
	 *------------------------------------------------------------------------------------*/
    /**
     * Actualiza una asistencia de clase aeróbica existente.
     *
     * @param id ID de la asistencia a actualizar
     * @param dto DTO con los datos actualizados
     * @return ResponseEntity con el estado y el DTO de respuesta
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarAsistenciaClaseAerobica(@PathVariable Integer id, @RequestBody AsistenciaClaseAerobicaActualizarDTO dto){
    
        AsistenciaClaseAerobica asistencia = asistenciaClaseAerobicaServicio.actualizarAsistenciaClaseAerobica(id, dto);
        return ResponseEntity.ok(asistenciaClaseAerobicaServicio.toResponseDTO(asistencia));
    }

    /*-------------------------------------------------------------------------------------
     * 3. Obtener asistencia clase aerobica por id (api/asistencia-clase-aerobica/{id})
     *------------------------------------------------------------------------------------*/
    /**
     * Obtiene una asistencia de clase aeróbica por su ID.
     *
     * @param id ID de la asistencia a buscar
     * @return ResponseEntity con el estado y el DTO de respuesta o un mensaje de error si no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerAsistenciaPorId(@PathVariable Integer id) {

        return asistenciaClaseAerobicaServicio.obtenerAsistenciaPorId(id).<ResponseEntity<?>>map(a -> ResponseEntity.ok(asistenciaClaseAerobicaServicio.toResponseDTO(a)))
                                                                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                                        .body(Map.of("mensaje", "No se encontró la asistencia con ID " + id)));
    }

    /*--------------------------------------------------------------------------------------------------------
     * 4. Buscar asistencias clase aerobica por fecha, mes o rango de fechas (api/asistencia-clase-aerobica)
     *-------------------------------------------------------------------------------------------------------*/
    /**
     * Busca asistencias de clase aeróbica por fecha.
     *
     * @param fecha Fecha de la asistencia a buscar
     * @return ResponseEntity con el estado y la lista de asistencias o un mensaje de error si no se encuentran
     */
    @GetMapping(params = "fecha")
    public ResponseEntity<?> buscarAsistenciasPorFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
            // Buscar las ventas por fecha
            List<AsistenciaClaseAerobica> asistencias = asistenciaClaseAerobicaServicio.obtenerAsistenciasPorFecha(fecha);
            
            // Si no se encuentran asistencias, retornar un mensaje de error
            if(asistencias.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Asistencia no encontrada en la fecha proporcionada"));
            }
            
            return ResponseEntity.ok(asistenciaClaseAerobicaServicio.toResponseDTO(asistencias));
    }

    /**
     * Busca asistencias de clase aeróbica por mes y año.
     * @param anio Año del mes a buscar
     * @param mes Mes a buscar (1-12)
     * @return ResponseEntity con el estado y la lista de asistencias o un mensaje de error si no se encuentran
     */
    @GetMapping(params = {"anio", "mes"})
    public ResponseEntity<?> buscarPorMes(@RequestParam int anio, @RequestParam int mes){

        List<AsistenciaClaseAerobica> asistencias = asistenciaClaseAerobicaServicio.obtenerAsistenciasPorMes(anio, mes);
        
        if(asistencias.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Asistencia no encontrada en la fecha proporcionada"));
        }
        
        return ResponseEntity.ok(asistenciaClaseAerobicaServicio.toResponseDTO(asistencias));
    }

    /**
     * Busca asistencias de clase aeróbica por un rango de fechas.
     *
     * @param inicio Fecha de inicio del rango
     * @param fin Fecha de fin del rango
     * @return ResponseEntity con el estado y la lista de asistencias o un mensaje de error si no se encuentran
     */
    @GetMapping(params = {"inicio", "fin"})
    public ResponseEntity<?> buscarPorRangoFechas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio, 
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin){
        
        List<AsistenciaClaseAerobica> asistencias = asistenciaClaseAerobicaServicio.obtenerAsistenciasPorRangoFechas(inicio, fin);
        
        if(asistencias.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Asistencia no encontrada en el rango de fecha proporcionado"));
        }
        
        return ResponseEntity.ok(asistenciaClaseAerobicaServicio.toResponseDTO(asistencias));
    }
}
