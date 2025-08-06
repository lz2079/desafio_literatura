package com.alura.desafio.literatura.entity;

import com.alura.desafio.literatura.model.Book;
import com.alura.desafio.literatura.entity.Author;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long idGutendex;

    private String titulo;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Author> autores = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private List<String> idiomas = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "libro_temas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "tema")
    private List<String> bookshelves = new ArrayList<>();


    private Integer numeroDescargas;


    public Libro() {}


    public Libro(com.alura.desafio.literatura.model.Book bookAPI) {
        this.idGutendex = bookAPI.getId();
        this.titulo = bookAPI.getTitulo();
        this.numeroDescargas = bookAPI.getNumeroDescargas();

        if (bookAPI.getIdiomas() != null) {
            this.idiomas.addAll(bookAPI.getIdiomas());
        }

        if (bookAPI.getBookshelves() != null) {
            this.bookshelves.addAll(bookAPI.getBookshelves());
        }

    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdGutendex() { return idGutendex; }
    public void setIdGutendex(Long idGutendex) { this.idGutendex = idGutendex; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public List<Author> getAutores() { return autores; }
    public void setAutores(List<Author> autores) { this.autores = autores; }

    public List<String> getIdiomas() { return idiomas; }
    public void setIdiomas(List<String> idiomas) { this.idiomas = idiomas; }


    public List<String> getBookshelves() { return bookshelves; }
    public void setBookshelves(List<String> bookshelves) { this.bookshelves = bookshelves; }


    public Integer getNumeroDescargas() { return numeroDescargas; }
    public void setNumeroDescargas(Integer numeroDescargas) { this.numeroDescargas = numeroDescargas; }


    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", idGutendex=" + idGutendex +
                ", titulo='" + titulo + '\'' +
                ", autores=" + (autores != null ? autores.size() + " autor(es)" : "[]") +
                ", idiomas=" + idiomas +
                ", bookshelves=" + bookshelves +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }
}