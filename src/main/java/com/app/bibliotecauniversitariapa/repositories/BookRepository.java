package com.app.bibliotecauniversitariapa.repositories;

import com.app.bibliotecauniversitariapa.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Devuelve todos los libros asociados a un autor específico.
     */
    List<Book> findByAuthorId(Long authorId);

    /**
     * Verifica si existe un libro con un ISBN determinado.
     */
    boolean existsByIsbn(String isbn);

    /**
     * Lista todos los libros con su autor (fetch join) para evitar N+1.
     * (Si tu versión de Java no soporta text blocks, pásalo a un string normal).
     */
    @Query("""
        SELECT b FROM Book b
        JOIN FETCH b.author a
        ORDER BY a.lastName, a.firstName, b.title
    """)
    List<Book> findAllWithAuthor();
}
