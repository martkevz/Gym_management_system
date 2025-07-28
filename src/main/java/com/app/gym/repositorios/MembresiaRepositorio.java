package com.app.gym.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.gym.modelos.Membresia;

public interface MembresiaRepositorio extends JpaRepository<Membresia, Integer> {

}
