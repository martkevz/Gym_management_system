package com.app.gym.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	/*--------------------------------------------------------------
	 * 1. Registrar asistencia general
	 *-------------------------------------------------------------*/

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

}