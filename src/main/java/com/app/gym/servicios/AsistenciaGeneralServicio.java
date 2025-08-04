package com.app.gym.servicios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.gym.dtos.AsistenciaGeneralActualizarDTO;
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

import org.springframework.transaction.annotation.Transactional;


@Service
public class AsistenciaGeneralServicio {
    
    private final UsuarioRepositorio usuarioRepositorio;
    private final HorarioPorDiaRepositorio horarioRepositorio;
    private final AsistenciaGeneralRepositorio asistenciaGeneralRepositorio;

    public AsistenciaGeneralServicio(HorarioPorDiaRepositorio horarioRepositorio, AsistenciaGeneralRepositorio asistenciaGeneralRepositorio,
                                    UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.horarioRepositorio = horarioRepositorio;
        this.asistenciaGeneralRepositorio = asistenciaGeneralRepositorio;
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
        /*  
         * Validar que el usuario tenga una membresía activa.
         * Si la fecha de fin de la membresía es nula o anterior a la fecha actual, se lanza una excepción.
         */
        if (usuario.getFechaFinMembresia() == null || usuario.getFechaFinMembresia().toLocalDate().isBefore(LocalDate.now())){
            throw new IllegalStateException("El usuario no tiene una membresía activa");
        }

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

        // Retornar la venta guardada
        return asistenciaGeneralRepositorio.save(asistencia); // Guarda la asistencia y retorna la entidad guardada, que ahora tiene el ID asignado.
    }

    // ------------------------------------------------------------------
    // Operaciones de actualización
    // ------------------------------------------------------------------

    /**
     * Actualiza una asistencia general existente.
     *
     * @param idAsistencia el ID de la asistencia a actualizar
     * @param fecha la fecha de la asistencia a actualizar
     * @param dto los nuevos datos de la asistencia
     * @return la asistencia actualizada
     */
    @Transactional
	public AsistenciaGeneral actualizarAsistenciaGeneral(Integer idAsistencia, LocalDate fecha, AsistenciaGeneralActualizarDTO dto){

        AsistenciaGeneral asistencia = asistenciaGeneralRepositorio.findByIdAsistenciaAndFecha(idAsistencia, fecha)
                                                                    .orElseThrow(() -> new RecursoNoEncontradoExcepcion
                                                                    ("Asistencia no encontrada con ID: " + idAsistencia + " y fecha: " + fecha));

        //Actualizar los campos de la venta con los datos del DTO y el trigger va a recalculará total
        asistencia.setHoraEntrada(dto.getHoraEntrada());

        // Si el DTO indica que la asistencia está anulada, se actualiza el estado de la asistencia
        if(Boolean.TRUE.equals(dto.getAnulada())){
            asistencia.setAnulada(dto.getAnulada());
        }

        // Retornar la asistencia.
        return asistenciaGeneralRepositorio.save(asistencia);
    }

    // ------------------------------------------------------------------
    // Operaciones de consulta
    // ------------------------------------------------------------------

    /**
     * Busca una asistencia por su ID y fecha.
     *
     * @param id el ID de la Asistencia
     * @param fecha la fecha de la Asitencia
     * @return un Optional que contiene la asistencia si se encuentra, o vacío si no
     */
    @Transactional(readOnly = true)
    public Optional<AsistenciaGeneral> buscarPorIdFecha (Integer idAsistencia, LocalDate fecha){
        return asistenciaGeneralRepositorio.findByIdAsistenciaAndFecha(idAsistencia, fecha);
    }

    /**
     * Busca todas las asistencias realizadas en una fecha específica.
     *
     * @param fecha la fecha de la asistencia
     * @return una lista de asistencias realizadas en esa fecha
     */
    @Transactional(readOnly = true)
    public List<AsistenciaGeneral> buscarPorFecha(LocalDate fecha){
        return asistenciaGeneralRepositorio.findByFecha(fecha);
    }

    /**
     * Busca todas las asistencias realizadas en un mes específico.
     *
     * @param year el año del mes a buscar
     * @param month el mes a buscar
     * @return una lista de asistencias realizadas en ese mes
     */
    @Transactional(readOnly = true)
    public List<AsistenciaGeneral> buscarPorMes(int year, int month){
        YearMonth ym = YearMonth.of(year, month);
        return asistenciaGeneralRepositorio.findByRangoFechas(ym.atDay(1), ym.atEndOfMonth());
    }

    /**
     * Busca todas las asistencias que hay en un rango de fechas.
     *
     * @param inicio la fecha de inicio del rango
     * @param fin la fecha de fin del rango
     * @return una lista de asistencias realizadas en el rango de fechas
     */
    @Transactional(readOnly = true)
    public List<AsistenciaGeneral> buscarPorRangoFechas(LocalDate inicio, LocalDate fin){
        return asistenciaGeneralRepositorio.findByRangoFechas(inicio, fin);
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

        UsuarioSimpleDTO u = new UsuarioSimpleDTO();
        u.setIdUsuario(asistenciaGeneral.getUsuario().getIdUsuario());
        u.setNombre(asistenciaGeneral.getUsuario().getNombre());
        u.setApellido(asistenciaGeneral.getUsuario().getApellido());

        AsistenciaGeneralResponseDTO r = new AsistenciaGeneralResponseDTO();
        r.setIdAsistencia(asistenciaGeneral.getIdAsistencia());
        r.setFecha(asistenciaGeneral.getFecha());
        r.setHoraEntrada(asistenciaGeneral.getHoraEntrada());
        r.setAnulada(asistenciaGeneral.getAnulada());
        r.setUsuario(u);
        r.setHorario(h);

        return r; // Retorna el DTO de respuesta con los datos de la asistencia general
    }

    /**
     * Convierte una lista de entidades AsistenciaGeneral a una lista de DTOs de respuesta.
     *
     * Este método utiliza la API de Streams de Java para transformar una lista de
     * objetos del tipo AsistenciaGeneral en una lista de objetos AsistenciaGeneralResponseDTO. Esta transformación
     * es útil para exponer solo los datos necesarios al cliente, manteniendo así la capa de persistencia desacoplada de la capa de presentación.
     *
     * @param asistencias la lista de entidades AsistenciaGeneral a convertir
     * @return la lista de DTOs de respuesta correspondientes
     */
    public List<AsistenciaGeneralResponseDTO> toResponseDTO(List<AsistenciaGeneral> asistencias){

        // Inicia un Stream a partir de la lista de objetos AsistenciaGeneral.
        // Un Stream permite operar sobre los elementos de forma funcional y más expresiva
        return asistencias.stream() // Aplica una transformación (mapeo) a cada objeto AsistenciaGeneral en el Stream.
                    .map(this::toResponseDTO) // Aplica una transformación (mapeo) a cada objeto AsistenciaGeneral en el Stream.
                    .toList(); // Convierte el Stream resultante (de tipo Stream<AsistenciaGeneralResponseDTO>) a una lista de tipo List<AsistenciaGeneralResponseDTO>.
    }
}
