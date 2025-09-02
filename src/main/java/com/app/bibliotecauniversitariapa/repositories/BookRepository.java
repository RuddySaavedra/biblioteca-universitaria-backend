package com.app.bibliotecauniversitariapa.repositories;

import com.app.bibliotecauniversitariapa.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
