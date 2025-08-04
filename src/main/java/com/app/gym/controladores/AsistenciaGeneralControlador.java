package com.app.gym.controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.gym.dtos.AsistenciaGeneralActualizarDTO;
import com.app.gym.dtos.AsistenciaRequestDTO;
import com.app.gym.modelos.AsistenciaGeneral;
import com.app.gym.servicios.AsistenciaGeneralServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/asistencia-general")
public class AsistenciaGeneralControlador {
    
    private final AsistenciaGeneralServicio asistenciaGeneralServicio;

    public AsistenciaGeneralControlador(AsistenciaGeneralServicio asistenciaGeneralServicio) {
        this.asistenciaGeneralServicio = asistenciaGeneralServicio;
    }

	/*---------------------------------------------------------------------
	 * 1. Registrar asistencia general (api/asistencia-general/registrar)
	 *--------------------------------------------------------------------*/

	/**
	 * Registra una nueva asistencia general.
	 *
	 * @param dto los datos de la asistencia a registrar
	 * @param br  el resultado de la validación
	 * @return la asistencia registrada o un error si hay problemas de validación
	 */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarAsistenciaGeneral (@Valid @RequestBody AsistenciaRequestDTO dto, BindingResult br){ //@valid valida las notaciones (como @NotNull) del DTO
	
		// Verifica si hay errores de validación en el objeto 'br'
        if (br.hasErrors()) {
            // Si hay errores, los recoge y los convierte en una lista de cadenas formateadas mediante "stream()"
            List<String> errores = br.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage()) // Mapea cada error a una cadena con el formato "campo: mensaje de error"
                    .toList(); // Convierte el stream en una lista

            // Retorna una respuesta HTTP con el estado 400 (Bad Request) y el cuerpo con los errores de validación
            return ResponseEntity.badRequest().body(Map.of("errores", errores));
        }

		AsistenciaGeneral asistencia = asistenciaGeneralServicio.registrarAsistenciaGeneral(dto);
	
		return ResponseEntity.status(HttpStatus.CREATED).body(asistenciaGeneralServicio.toResponseDTO(asistencia));
	}

    /*-----------------------------------------------------------------------------------------------------------------------------
     * 2. Actualizar asistencia. (horaEntrada y anulada)    /api/asistencia-general/actualizar/1/2025-01-05   formato de la fecha: YYYY-MM-DD
     *---------------------------------------------------------------------------------------------------------------------------*/

    /**
     * Actualiza una asistencia general existente.
     * @param idAsistencia el ID de la asistencia a actualizar
     * @param fecha la fecha de la asistencia a actualizar
     * @param dto los datos de la asistencia a actualizar
     * @param br el resultado de la validación
     * @return la asistencia actualizada o un error si hay problemas de validación
     */
    @PutMapping("/{id}/{fecha}")
    public ResponseEntity<?> actualizarAsistenciaGeneral(@PathVariable Integer id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                                        @RequestBody AsistenciaGeneralActualizarDTO dto){
        
        AsistenciaGeneral asistencia = asistenciaGeneralServicio.actualizarAsistenciaGeneral(id, fecha, dto);

        return ResponseEntity.ok(asistenciaGeneralServicio.toResponseDTO(asistencia));      
    }

    /*-----------------------------------------------------------------------------------------------------------------------------
     * 3. Buscar asistencia por PK. /api/asistencia-general/{id}/{fecha}   formato de la fecha: YYYY-MM-DD
     *---------------------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Busca una asistencia general por su ID y fecha.
     * @param idAsistencia el ID de la asistencia a buscar
     * @param fecha la fecha de la asistencia a buscar
     * @return una respuesta HTTP con la asistencia encontrada o un mensaje de error si no se encuentra
     */
    @GetMapping("/{id}/{fecha}")
    public ResponseEntity<?> buscarPorIdFecha (@PathVariable Integer id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){

	return asistenciaGeneralServicio.buscarPorIdFecha(id, fecha).<ResponseEntity<?>>map(a -> ResponseEntity.ok(asistenciaGeneralServicio.toResponseDTO(a)))
                                                                                            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                                                            .body(Map.of("mensaje", "No se ha encontrado una asistencia con el id '" + id + "' en la fecha: '" + fecha +"'.")));
    }

    /*-----------------------------------------------------------------------------------------------------------------------------
     * 4. Asistencias de un día. /api/asistencia-general?fecha=2025-01-01   formato de la fecha: YYYY-MM-DD
     *---------------------------------------------------------------------------------------------------------------------------*/

    /**
      * Busca todas las asistencias generales registradas en una fecha específica.
      * @param fecha la fecha para buscar las asistencias
      * @return una lista de asistencias encontradas o un mensaje de error si no se encuentran    
      */
    @GetMapping(params = "fecha")
    public ResponseEntity<?> buscarAsistenciasPorFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        
        // Buscar las ventas por fecha
        List<AsistenciaGeneral> asistencias = asistenciaGeneralServicio.buscarPorFecha(fecha);
        
        // Si no se encuentran asistencias, retornar un mensaje de error
        if(asistencias.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Asistencia no encontrada en la fecha proporcionada"));
        }
        
        return ResponseEntity.ok(asistenciaGeneralServicio.toResponseDTO(asistencias));	
    }

    /**-----------------------------------------------------------------------------------------------------------------------------
     * 5. Asistencias por mes. /api/asistencia-general?anio=2025&mes=01    formato de la fecha: YYYY-MM-DD
     *-----------------------------------------------------------------------------------------------------------------------------*/
    
    /**
     * Busca todas las asistencias generales registradas en un mes específico.
     * @param anio el año para buscar las asistencias
     * @param mes el mes para buscar las asistencias
     * @return una lista de asistencias encontradas o un mensaje de error si no se encuentran
     */
    @GetMapping(params = {"anio", "mes"})
    public ResponseEntity<?> buscarPorMes(@RequestParam int anio, @RequestParam int mes){
        
        // Buscar las ventas por mes
        List<AsistenciaGeneral> asistencias = asistenciaGeneralServicio.buscarPorMes(anio, mes);
        
        // Si no se encuentran asistencias, retornar un mensaje de error
        if(asistencias.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Sin asistencias para el período indicado"));
        }
        
        return ResponseEntity.ok(asistenciaGeneralServicio.toResponseDTO(asistencias));
    }

    /**-----------------------------------------------------------------------------------------------------------------------------
     * 6. Asistencias por rango de fechas. /api/asistencia-general?inicio=2025-01-01&fin=2025-01-31   formato de la fecha: YYYY-MM-DD
     *-----------------------------------------------------------------------------------------------------------------------------*/

    /**
     * Busca todas las asistencias generales registradas en un rango de fechas específico.
     * @param inicio la fecha de inicio del rango
     * @param fin la fecha de fin del rango
     * @return una lista de asistencias encontradas o un mensaje de error si no se encuentran
     */
    @GetMapping(params = {"inicio", "fin"})
    public ResponseEntity<?> buscarPorRangoFechas (@RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate inicio, 
                                                    @RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate fin){
        // Buscar las asistencias por rango de fechas
        List<AsistenciaGeneral> asistencias = asistenciaGeneralServicio.buscarPorRangoFechas(inicio, fin);
        
        // Si no se encuentran asistencias, retornar un mensaje de error
        if(asistencias.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "No hay asistencias en el rango de fecha proporcionado."));
        }
        
        // Retornar las asistencias encontradas
        return ResponseEntity.ok(asistenciaGeneralServicio.toResponseDTO(asistencias));
    }

}