package com.alura.desafio.literatura.repository;

import com.alura.desafio.literatura.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByIdGutendex(Long idGutendex);
    List<Libro> findByIdiomasContaining(String idioma);
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    @Query("SELECT DISTINCT l FROM Libro l JOIN l.bookshelves b WHERE LOWER(b) LIKE LOWER(CONCAT('%', :bookshelfParte, '%'))")
    List<Libro> findByBookshelvesContainingIgnoreCase(@Param("bookshelfParte") String bookshelfParte);
    Long countByIdiomasContaining(String idioma);
}