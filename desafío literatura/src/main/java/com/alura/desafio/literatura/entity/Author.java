package com.alura.desafio.literatura.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "autores") // Nombre de la tabla en la base de datos
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-incremental
    private Long id;

    @Column(unique = true) // Evita duplicados de nombres
    private String nombre;

    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    // --- Constructores ---
    public Author() {} // Constructor por defecto (requerido por JPA)

    // Constructor útil para crear un Autor desde un modelo de API (opcional)
    public Author(com.alura.desafio.literatura.model.Author authorAPI) {
        this.nombre = authorAPI.getNombre();
        this.fechaNacimiento = authorAPI.getFechaNacimiento();
        this.fechaFallecimiento = authorAPI.getFechaFallecimiento();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Integer fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Integer getFechaFallecimiento() { return fechaFallecimiento; }
    public void setFechaFallecimiento(Integer fechaFallecimiento) { this.fechaFallecimiento = fechaFallecimiento; }


    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaFallecimiento=" + fechaFallecimiento +
                '}';
    }
}