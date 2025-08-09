package com.app.gym.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.gym.dtos.membresia.MembresiaSimpleDTO;
import com.app.gym.dtos.usuario.UsuarioRequestDTO;
import com.app.gym.dtos.usuario.UsuarioResponseDTO;
import com.app.gym.modelos.Membresia;
import com.app.gym.modelos.Usuario;
import com.app.gym.repositorios.MembresiaRepositorio;
import com.app.gym.repositorios.UsuarioRepositorio;
import com.app.gym.validadores.FechaValidador;

import jakarta.persistence.EntityManager;

@Service
public class UsuarioServicio {
    
    UsuarioRepositorio usuarioRepositorio;
    MembresiaRepositorio membresiaRepositorio;
    EntityManager entityManager;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, MembresiaRepositorio membresiaRepositorio, EntityManager entityManager) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.membresiaRepositorio = membresiaRepositorio;
        this.entityManager = entityManager;
    }
    // ------------------------------------------------------------------
    // Operaciones de creación
    // ------------------------------------------------------------------

    @Transactional
    public Usuario registrarUsuario(UsuarioRequestDTO dto){

        //validar que la membresía exista
        Membresia membresia = membresiaRepositorio.findById(dto.getIdMembresia())
                                                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));
        
        FechaValidador.validarFechaNacimiento(dto.getFechaNacimiento());

        LocalDate fecha = dto.getFechaInicioMembresia() != null ? dto.getFechaInicioMembresia() : LocalDate.now();
        
        FechaValidador.validarFecha(fecha);
        FechaValidador.validarFechaNacimiento(dto.getFechaNacimiento());

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setEmail(dto.getEmail());
        usuario.setFechaInicioMembresia(fecha);
        usuario.setMembresia(membresia);

        Usuario usuarioActualizado = usuarioRepositorio.saveAndFlush(usuario);

        entityManager.refresh(usuarioActualizado);

        return usuarioActualizado;
    }

    /**
     * Convierte un objeto Usuario a UsuarioResponseDTO.    
     * @param usuario El objeto Usuario a convertir.
     * @return Un objeto UsuarioResponseDTO con los datos del usuario.
     */
    public UsuarioResponseDTO toResponseDTO(Usuario usuario){
        MembresiaSimpleDTO m = new MembresiaSimpleDTO();
        m.setIdMembresia(usuario.getMembresia().getIdMembresia());
        m.setNombre(usuario.getMembresia().getNombre());

        UsuarioResponseDTO u = new UsuarioResponseDTO();
        u.setIdUsuario(usuario.getIdUsuario());
        u.setNombre(usuario.getNombre());
        u.setApellido(usuario.getApellido());
        u.setFechaNacimiento(usuario.getFechaNacimiento());
        u.setEmail(usuario.getEmail());
        u.setFechaInicioMembresia(usuario.getFechaInicioMembresia());
        u.setFechaFinMembresia(usuario.getFechaFinMembresia());
        u.setAnulada(usuario.getAnulada());
        u.setMembresia(m);

        return u;
    }

    /**
     * Convierte una lista de objetos Usuario a una lista de UsuarioResponseDTO.
     * @param usuarios La lista de objetos Usuario a convertir.
     * @return Una lista de UsuarioResponseDTO con los datos de los usuarios.
     */
    public List<UsuarioResponseDTO> toResponseDTO(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toResponseDTO).toList();
    }
}
