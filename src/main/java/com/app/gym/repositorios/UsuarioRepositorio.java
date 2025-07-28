package com.app.gym.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.gym.modelos.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    
}
