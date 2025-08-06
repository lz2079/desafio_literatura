package com.alura.desafio.literatura.repository;

import com.alura.desafio.literatura.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.fechaNacimiento <= :año AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :año)")
    List<Author> findAutoresVivosEnAño(Integer año);

    Optional<Author> findByNombre(String nombre);
}
