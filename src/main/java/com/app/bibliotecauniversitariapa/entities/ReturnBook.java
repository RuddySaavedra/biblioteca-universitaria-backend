package com.app.bibliotecauniversitariapa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "returns")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReturnBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate returnDate;
    private BigDecimal penaltyAmount;
    private String notes;
}
