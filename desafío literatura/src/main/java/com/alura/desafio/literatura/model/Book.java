package com.alura.desafio.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @JsonAlias("id")
    private Long id;

    @JsonAlias("title")
    private String titulo;

    @JsonAlias("authors")
    private List<Author> autores;

    @JsonAlias("bookshelves")
    private List<String> bookshelves;

    @JsonAlias("languages")
    private List<String> idiomas;

    @JsonAlias("download_count")
    private Integer numeroDescargas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Author> getAutores() {
        return autores;
    }

    public void setAutores(List<Author> autores) {
        this.autores = autores;
    }

    public List<String> getBookshelves() {
        return bookshelves;
    }

    public void setBookshelves(List<String> bookshelves) {
        this.bookshelves = bookshelves;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", bookshelves=" + bookshelves +
                ", idiomas=" + idiomas +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }
}