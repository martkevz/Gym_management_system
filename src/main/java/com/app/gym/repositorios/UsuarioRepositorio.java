package com.app.gym.repositorios;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.gym.modelos.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    
    /** Busca un usuario por su ID.
     * @param idUsuario el ID del usuario
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<Usuario> findByIdUsuario(Integer idUsuario);

    /** Busca un usuario por su email.
     * @param email el email del usuario
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    Optional<Usuario> findByEmail(String email);
    
    /** Para búsquedas parciales por nombre o apellido
     * @param nombre el nombre del usuario
     * @param apellido el apellido del usuario
     * @return un Optional que contiene el usuario si se encuentra, o vacío si no
     */
    List<Usuario> findByNombreIgnoreCaseContainingOrApellidoIgnoreCaseContaining(String nombre, String apellido);

    /** Busca todos los usuarios cuya membresía está por vencer en los próximos 7 días.
     * @return una lista de usuarios con membresía por vencer
     */
    @Query("SELECT u FROM Usuario u WHERE u.fechaFinMembresia BETWEEN :inicio AND :fin")
    List<Usuario> findUsuariosPorVencer(@Param("inicio") Date inicio, @Param("fin") Date fin);
}

