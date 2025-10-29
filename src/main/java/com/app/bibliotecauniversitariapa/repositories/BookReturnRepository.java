package com.app.bibliotecauniversitariapa.repositories;

import com.app.bibliotecauniversitariapa.entities.BookReturn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReturnRepository extends JpaRepository<BookReturn, Long> {
}
