package com.app.bibliotecauniversitariapa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "book_returns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate returnDate;
    private double penaltyAmount;
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false, foreignKey = @ForeignKey(name = "fk_return_loan"))
    private Loan loan;
}