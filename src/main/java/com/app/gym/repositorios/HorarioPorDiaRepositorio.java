package com.app.gym.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.gym.modelos.HorarioPorDia;

public interface HorarioPorDiaRepositorio extends JpaRepository<HorarioPorDia, Integer>{
    
}
