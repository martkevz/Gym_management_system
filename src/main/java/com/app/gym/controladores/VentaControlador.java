package com.app.gym.controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

import com.app.gym.dtos.venta.VentaActualizarDto;
import com.app.gym.dtos.venta.VentaCrearDto;
import com.app.gym.servicios.VentaServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ventas")
public class VentaControlador {

    private final VentaServicio ventaServicio;

    public VentaControlador(VentaServicio ventaServicio) {
        this.ventaServicio = ventaServicio;
    }

    /*--------------------------------------------------------------
     * 1. Crear venta. /api/ventas/registrar
     *-------------------------------------------------------------*/
    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVenta(@Valid @RequestBody VentaCrearDto dto, BindingResult br) { //@valid valida las notaciones (como @NotNull) del DTO

        if (br.hasErrors()) { // Verifica si hay errores de validación en el objeto 'br'
            // Si hay errores, los recoge y los convierte en una lista de cadenas formateadas mediante "stream()"
            List<String> errores = br.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage()) // Mapea cada error a una cadena con el formato "campo: mensaje de error"
                    .toList(); // Convierte el stream en una lista

            return ResponseEntity.badRequest().body(Map.of("errores", errores));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaServicio.toResponseDTO(ventaServicio.registrarVenta(dto))); // Si no hay errores, continua con la lógica normal
    }

    /*------------------------------------------------------------------------------------------------------------
     * 2. Actualiza una venta (cantidad / anular). /api/ventas/{id}/{fecha}     formato de la fecha: YYYY-MM-DD
     *-----------------------------------------------------------------------------------------------------------*/
    
    @PatchMapping("/{id}/{fecha}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Integer id,
                                            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                            @Valid @RequestBody VentaActualizarDto dto, BindingResult br) {
        if (br.hasErrors()) {
            List<String> errores = br.getFieldErrors().stream()
                                                        .map(e -> e.getField() + ": " + e.getDefaultMessage())
                                                        .toList();
            return ResponseEntity.badRequest().body(Map.of("errores", errores));

        }
        return ResponseEntity.ok(ventaServicio.toResponseDTO(ventaServicio.actualizarVenta(id, fecha, dto)));
    }

    /*----------------------------------------------------------------------------
     * 3. Buscar venta por PK (ID y fecha).      /api/ventas/{id}/{fecha}   
     *----------------------------------------------------------------------------*/
    
    @GetMapping("/{id}/{fecha}") 
	public ResponseEntity<?> buscarVentaPorIdFecha(@PathVariable Integer id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        return ResponseEntity.ok(ventaServicio.toResponseDTO(ventaServicio.buscarPorIdFecha(id, fecha))); 
	}

    /*--------------------------------------------------------------------------------------------------
     * 4. Busca todas las ventas realizadas en una fecha específica.   /api/ventas?fecha=2025-01-01 
     *------------------------------------------------------------------------------------------------*/
    
    @GetMapping(params = "fecha") 
    public ResponseEntity<?> buscarVentasPorFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        return ResponseEntity.ok(ventaServicio.toResponseDTO(ventaServicio.buscarPorFecha(fecha)));
    }

    /*-----------------------------------------------------------------------------------------------
     * 5. Busca todas las ventas realizadas en un mes específico.   /api/ventas?anio=2025&mes=01    
     *----------------------------------------------------------------------------------------------*/
    
    @GetMapping(params = {"anio", "mes"})
    public ResponseEntity<?> buscarPorMes(@RequestParam int anio, @RequestParam int mes){
        return ResponseEntity.ok(ventaServicio.toResponseDTO(ventaServicio.buscarPorMes(anio, mes)));
    }

    /*----------------------------------------------------------------------------------------------------------------
     * 6. Busca todas las ventas realizadas en un rango de fechas.     /api/ventas?inicio=2025-01-01&fin=2025-01-31   
     *----------------------------------------------------------------------------------------------------------------*/
    
    @GetMapping(params = {"inicio", "fin"})
    public ResponseEntity<?> buscarPorRangoFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
												@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin){
        return ResponseEntity.ok(ventaServicio.toResponseDTO(ventaServicio.buscarPorRangoFechas(inicio, fin)));
    }
}