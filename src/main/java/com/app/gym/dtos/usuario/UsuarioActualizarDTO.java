package com.app.gym.dtos.usuario;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;

public class UsuarioActualizarDTO {

    private String nombre;
    private String apellido;
    private String fechaNacimiento;

    @Email(message = "El email debe ser válido")
    private String email;

    private LocalDate fechaInicioMembresia;
    public Boolean anulada; 
    private Integer membresia;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getFechaInicioMembresia() {
        return fechaInicioMembresia;
    }
    public void setFechaInicioMembresia(LocalDate fechaInicioMembresia) {
        this.fechaInicioMembresia = fechaInicioMembresia;
    }
    public Boolean getAnulada() {
        return anulada;
    }
    public void setAnulada(Boolean anulada) {
        this.anulada = anulada;
    }
    public Integer getMembresia() {
        return membresia;
    }
    public void setMembresia(Integer membresia) {
        this.membresia = membresia;
    }
}
