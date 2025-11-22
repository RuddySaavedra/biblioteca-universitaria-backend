package com.app.bibliotecauniversitariapa.repositories;

import com.app.bibliotecauniversitariapa.entities.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
}
