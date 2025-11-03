package com.app.bibliotecauniversitariapa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_id", unique = true, foreignKey = @ForeignKey(name = "fk_book_return_loan"))
    @JsonBackReference
    private Loan loan;
}