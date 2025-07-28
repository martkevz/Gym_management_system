package com.app.gym.repositorios;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.gym.modelos.AsistenciaGeneral;
import com.app.gym.modelos.AsistenciaId;

public interface AsistenciaGeneralRepositorio extends JpaRepository<AsistenciaGeneral, AsistenciaId> {

    /**
     * Crear el id manualmente para las asistencia general en un mes específico.
     * @param inicio la fecha de inicio del mes
     * @param fin la fecha de fin del mes  
     * @return el ID máximo de venta encontrado en el mes, o 0 si no hay ventas
     */
    @Query(value = "SELECT COALESCE(MAX(a.id_asistencia), 0) FROM asistencia_general a WHERE a.fecha BETWEEN :inicio AND :fin", nativeQuery = true)
    Integer findMaxIdAsistenciabyMonth (@Param ("inicio") LocalDate inicio, @Param ("fin") LocalDate fin);
    
}