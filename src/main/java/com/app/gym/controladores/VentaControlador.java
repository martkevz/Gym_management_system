package com.app.gym.controladores;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
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

import com.app.gym.dtos.VentaActualizarDto;
import com.app.gym.dtos.VentaCrearDto;
import com.app.gym.modelos.Venta;
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
     * 1. Crear venta
     *-------------------------------------------------------------*/

    /**
     * Registra una nueva venta.
     *
     * @param dto los datos de la venta a registrar
     * @param br  el resultado de la validación
     * @return la venta registrada o un error si hay problemas de validación
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVenta(@Valid @RequestBody VentaCrearDto dto, BindingResult br) { //@valid valida las notaciones (como @NotNull) del DTO

        // Verifica si hay errores de validación en el objeto 'br'
        if (br.hasErrors()) {
            // Si hay errores, los recoge y los convierte en una lista de cadenas formateadas mediante "stream()"
            List<String> errores = br.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage()) // Mapea cada error a una cadena con el formato "campo: mensaje de error"
                    .toList(); // Convierte el stream en una lista

            // Retorna una respuesta HTTP con el estado 400 (Bad Request) y el cuerpo con los errores de validación
            return ResponseEntity.badRequest().body(Map.of("errores", errores));
        }

        Venta ventas = ventaServicio.registrarVenta(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ventaServicio.toResponseDTO(ventas)); // Si no hay errores, continua con la lógica normal
    }

    /*--------------------------------------------------------------
     * 2. Actualizar venta (cantidad / anular)
     *-------------------------------------------------------------*/

    /**
     * Actualiza una venta existente.
     * 
     * @param id    el ID de la venta a actualizar
     * @param fecha la fecha de la venta a actualizar
     * @param dto   los nuevos datos de la venta
     */
    @PutMapping("/{id}/{fecha}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Integer id,
                                            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                            @Valid @RequestBody VentaActualizarDto dto, BindingResult br) {
        if (br.hasErrors()) {
            List<String> errores = br.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .toList();
            return ResponseEntity.badRequest().body(Map.of("errores", errores));

        }
        Venta actualizada = ventaServicio.actualizarVenta(id, fecha, dto);
        return ResponseEntity.ok(ventaServicio.toResponseDTO(actualizada));
    }

    /*--------------------------------------------------------------
     * 3. Buscar venta por PK
     *-------------------------------------------------------------*/
    
    /**
     * Busca una venta por su ID y fecha.
     * @param id    el ID de la venta
     * @param fecha la fecha de la venta
     * @return la venta encontrada o un error si no se encuentra
     */
    @GetMapping("/{id}/{fecha}") 
	public ResponseEntity<?> buscarVentaPorIdFecha(@PathVariable Integer id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
			
        return ventaServicio.buscarPorIdFecha(id, fecha).<ResponseEntity<?>>map(v -> ResponseEntity.ok(ventaServicio.toResponseDTO(v)))
                                                                            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                                            .body(Map.of("mensaje", "Venta no encontrada")));
	}

    /*--------------------------------------------------------------
     * 4. Ventas de un día  (?fecha=YYYY-MM-DD)
     *-------------------------------------------------------------*/
    /**
     * Busca todas las ventas realizadas en una fecha específica.
     * @param fecha la fecha de las ventas
     * @return una lista de ventas realizadas en esa fecha o un error si no se encuentran
     */
    @GetMapping(params = "fecha")
    public ResponseEntity<?> buscarVentaPorFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){

        // Obtener la fecha actual para validar el rango
        LocalDate fechaActual = LocalDate.now();

        // Validar que la fecha no sea futura o anterior al año 2024
        if(fecha.isAfter(fechaActual) || fecha.getYear() < 2024){
            return ResponseEntity.badRequest().body(Map.of("error", "Fecha inválida: fuera del rango permitido"));
        }

        // Buscar las ventas por fecha
		List<Venta> ventas = ventaServicio.buscarPorFecha(fecha);
		
        // Si no se encuentran ventas, retornar un mensaje de error
		if(ventas.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Venta no encontrada en la fecha proporcionada"));
		}
			return ResponseEntity.ok(ventaServicio.toResponseDTO(ventas));
    }

    /*--------------------------------------------------------------
     * 5. Ventas de un mes (?anio=YYYY&mes=MM)
     *-------------------------------------------------------------*/
    
    /**  
     * Busca todas las ventas realizadas en un mes específico.
     * @param anio el año del mes a buscar 
     * @param mes el mes a buscar
     * @return una lista de ventas realizadas en ese mes o un error si no se encuentran
     */
    @GetMapping(params = {"anio", "mes"})
    public ResponseEntity<?> buscarPorMes(@RequestParam int anio, @RequestParam int mes){

        // Validar que el mes y año sean válidos
        try {
            YearMonth fecha = YearMonth.of(anio, mes);
            if(fecha.isAfter(YearMonth.now()) || anio < 2024){
                    return ResponseEntity.badRequest().body(Map.of("error", "Fecha inválida: fuera del rango permitido"));
            } 
        } catch (DateTimeException e){
		return ResponseEntity.badRequest().body(Map.of("error", "Mes o año no válido."));  
        }
	
        // Buscar las ventas por mes
        List<Venta> ventas = ventaServicio.buscarPorMes(anio, mes); 
            if(ventas.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "Sin ventas para el período indicado"));
            }

        return ResponseEntity.ok(ventaServicio.toResponseDTO(ventas));
    }

    /*--------------------------------------------------------------
     * 6. Ventas por rango (?inicio=YYYY-MM-DD&fin=YYYY-MM-DD)
     *-------------------------------------------------------------*/

    /**
     * Busca todas las ventas realizadas en un rango de fechas.
     * @param inicio la fecha de inicio del rango
     * @param fin la fecha de fin del rango
     * @return una lista de ventas realizadas en el rango de fechas o un error si no se encuentran
     */
    @GetMapping(params = {"inicio", "fin"})
    public ResponseEntity<?> buscarPorRangoFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
												@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin){

    // Obtener la fecha actual para validar el rango
	LocalDate fechaActual = LocalDate.now();

    // Validar que el rango de fechas sea válido
	if(inicio.getYear() < 2024 || inicio.isAfter(fechaActual) || fin.isBefore(inicio)){
		return ResponseEntity.badRequest().body(Map.of("error", "Debe ingresar un rango de fecha válido (Nota: debe ser después de 2024)"));
	}
	
    // Buscar las ventas por rango de fechas
	List<Venta> ventas = ventaServicio.buscarPorRangoFechas(inicio, fin);
	
    // Si no se encuentran ventas, retornar un mensaje de error
	if(ventas.isEmpty()){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", "No hay ventas en el rango de fecha proporcionado."));
	}
    // Retornar las ventas encontradas
	return ResponseEntity.ok(ventaServicio.toResponseDTO(ventas));
    }
}