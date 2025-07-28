package com.app.gym.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

import org.springframework.stereotype.Service;

import com.app.gym.dtos.AsistenciaGeneralResponseDTO;
import com.app.gym.dtos.AsistenciaRequestDTO;
import com.app.gym.dtos.HorarioPorDiaSimpleDTO;
import com.app.gym.dtos.UsuarioSimpleDTO;
import com.app.gym.excepciones.RecursoNoEncontradoExcepcion;
import com.app.gym.modelos.AsistenciaGeneral;
import com.app.gym.modelos.HorarioPorDia;
import com.app.gym.modelos.Usuario;
import com.app.gym.repositorios.AsistenciaGeneralRepositorio;
import com.app.gym.repositorios.HorarioPorDiaRepositorio;
import com.app.gym.repositorios.UsuarioRepositorio;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


@Service
public class AsistenciaGeneralServicio {
    
    private final UsuarioRepositorio usuarioRepositorio;
    private final HorarioPorDiaRepositorio horarioRepositorio;
    private final AsistenciaGeneralRepositorio asistenciaGeneralRepositorio;
    private final EntityManager entityManager; // Inyecta el EntityManager para operaciones de persistencia

    public AsistenciaGeneralServicio(EntityManager entityManager, HorarioPorDiaRepositorio horarioRepositorio, AsistenciaGeneralRepositorio asistenciaGeneralRepositorio,
                                    UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.horarioRepositorio = horarioRepositorio;
        this.asistenciaGeneralRepositorio = asistenciaGeneralRepositorio;
        this.entityManager = entityManager; // Inicializa el EntityManager
    }

    // ------------------------------------------------------------------
    // Operaciones de creación
    // ------------------------------------------------------------------
    
    /**
     * Registra una nueva asistencia general.
     *
     * @param dto los datos de la asistencia a registrar
     * @return la asistencia registrada
     */
    @Transactional
    public AsistenciaGeneral registrarAsistenciaGeneral(AsistenciaRequestDTO dto){

        // Validar que el usuario y el horario existan
        Usuario usuario = usuarioRepositorio.findById(dto.getIdUsuario())
											.orElseThrow(() -> new RecursoNoEncontradoExcepcion("Usuario no encontrado con ID: " + dto.getIdUsuario()));

        HorarioPorDia horario = horarioRepositorio.findById(dto.getIdHorario())
                                                    .orElseThrow(() -> new RecursoNoEncontradoExcepcion ("Horario no encontrado con ID: " + dto.getIdHorario()));
        
        // Si la fecha no se proporciona, usar la fecha actual:
        LocalDate fecha = dto.getFecha() != null ? dto.getFecha() : LocalDate.now(); 

        // Si la hora de entrada no se proporciona, usar la hora actual:
        LocalTime horaEntrada = dto.getHoraEntrada() != null ? dto.getHoraEntrada() : LocalTime.now();

        // 1. Calcular rango del mes (partición)
        YearMonth yearMonth = YearMonth.from(fecha);
        LocalDate inicio = yearMonth.atDay(1);
        LocalDate fin = yearMonth.atEndOfMonth();

        // 2. Obtener el máximo ID en ese mes
        /*  
         * Obtener el máximo ID de asistencia en el mes actual (el cual lo calcula con la fecha "inicio" y "fin" dadas al metodo).
         * Esto permite que las asistencias se registren de manera secuencial por mes.
         * Si no hay asistencias, se iniciará con ID 1. 
         */
        Integer maxId = asistenciaGeneralRepositorio.findMaxIdAsistenciabyMonth(inicio, fin);
        int nuevoId = (maxId != null) ? maxId + 1 : 1;

        // Crear una nueva asistencia general
        // Asignar los valores a la nueva asistencia
        AsistenciaGeneral asistencia = new AsistenciaGeneral();
        asistencia.setIdAsistencia(nuevoId);
        asistencia.setFecha(fecha);
        asistencia.setHoraEntrada(horaEntrada);
        asistencia.setHorarioPorDia(horario);
        asistencia.setUsuario(usuario);

        /*
        * Guardar y forzar sincronización con la BD
        * es importante usar `saveAndFlush` para asegurarnos de que la operación de guardado se sincronice inmediatamente con la base de datos, 
        * de modo que el refresh pueda obtener los cambios.
        */
        AsistenciaGeneral asistenciaGuardada = asistenciaGeneralRepositorio.saveAndFlush(asistencia);

        // Refrescar después de guardar
        entityManager.refresh(asistenciaGuardada);

        // Retornar la venta guardada
        return asistenciaGuardada;
    }

    // ------------------------------------------------------------------
    public AsistenciaGeneralResponseDTO toResponseDTO (AsistenciaGeneral asistenciaGeneral){

        /*
         * Convierte una entidad AsistenciaGeneral a un DTO de respuesta.
         * Este método crea un DTO que contiene los detalles de la asistencia, incluyendo el horario y el usuario asociados.
         */
        HorarioPorDiaSimpleDTO h = new HorarioPorDiaSimpleDTO();
        h.setIdHorario(asistenciaGeneral.getHorarioPorDia().getIdHorario());
        h.setDia(asistenciaGeneral.getHorarioPorDia().getDia());
        h.setHoraApertura(asistenciaGeneral.getHorarioPorDia().getHoraApertura());
        h.setHoraCierre(asistenciaGeneral.getHorarioPorDia().getHoraCierre()); 

        UsuarioSimpleDTO u = new UsuarioSimpleDTO();
        u.setIdUsuario(asistenciaGeneral.getUsuario().getIdUsuario());
        u.setNombre(asistenciaGeneral.getUsuario().getNombre());
        u.setApellido(asistenciaGeneral.getUsuario().getApellido());

        AsistenciaGeneralResponseDTO r = new AsistenciaGeneralResponseDTO();
        r.setIdAsistencia(asistenciaGeneral.getIdAsistencia());
        r.setFecha(asistenciaGeneral.getFecha());
        r.setHoraEntrada(asistenciaGeneral.getHoraEntrada());
        r.setUsuario(u);
        r.setHorario(h);

        return r; // Retorna el DTO de respuesta con los datos de la asistencia general
    }


}
