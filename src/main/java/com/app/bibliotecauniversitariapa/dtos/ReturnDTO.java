package com.app.bibliotecauniversitariapa.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class ReturnDTO {
    private Long id;
    private LocalDate returnDate;
    private BigDecimal penaltyAmount;
    private String notes;
}
