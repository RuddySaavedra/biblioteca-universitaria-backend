package com.app.bibliotecauniversitariapa.repositories;

import com.app.bibliotecauniversitariapa.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
