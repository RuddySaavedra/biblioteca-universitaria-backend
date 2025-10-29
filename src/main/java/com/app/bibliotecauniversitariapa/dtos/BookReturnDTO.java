package com.app.bibliotecauniversitariapa.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookReturnDTO {
    private Long id;
    private LocalDate returnDate;
    private double penaltyAmount;
    private String reason;
}
