package com.app.gym.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Helpers genéricos para trabajar con listas:
 * - emptyIfNull: evita NPE (Null Pointer Exception) y devuelve lista vacía si la entrada es null
 * - mapList: mapea una lista a otra usando una función
 * - okMappedList: mapea y devuelve ResponseEntity.ok(listaDto) (útil en controladores)
 */
public class ListUtils {
    
    private ListUtils() {}       // Constructor privado para evitar instanciación

    // Evita NPE y devuelve una lista vacía si la entrada es null
    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * Mapea cada elemento de 'source' usando 'mapper' y devuelve la lista resultante.
     * @param source lista de entrada (puede ser null)
     * @param mapper función que convierte S -> T (ej: this::toResponseDTO)
     * @return lista mapeada (posiblemente vacía). Usamos stream().toList() 
     */
    public static <S, T> List<T> mapList(List<S> source, Function<? super S, T> mapper) {
        return emptyIfNull(source).stream().map(mapper).toList();
    }

    /**
     * Mapea la lista de entrada y devuelve ResponseEntity.ok(listaDto).
     * Útil para tener una sola línea en el controlador:
     * return ListUtils.okMappedList(ventas, ventaServicio::toResponseDTO)
     */
    public static <S, T> ResponseEntity<List<T>> okMappedList(List<S> source, Function<? super S, T> mapper) {
        List<T> dtos = mapList(source, mapper);
        return ResponseEntity.ok(dtos);
    }

    // Variante: si la lista mapeada queda vacía, devuelve 404 con mensaje
    public static <S, T> ResponseEntity<?> okOrNotFoundMappedList(List<S> source,
                                                                Function<? super S, T> mapper,
                                                                String mensajeVacio) {
        List<T> dtos = mapList(source, mapper);
        if (dtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensaje", mensajeVacio));
        }
        return ResponseEntity.ok(dtos);
    }
}
