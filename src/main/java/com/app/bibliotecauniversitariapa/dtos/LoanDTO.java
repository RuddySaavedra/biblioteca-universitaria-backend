package com.app.bibliotecauniversitariapa.dtos;

import com.app.bibliotecauniversitariapa.entities.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {
    private Long id;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LoanStatus status;
  
    private Long bookId;
    private String bookName;
  
    private Long returnId;
    // paso 3 datos que quiero mostrar del estudiante
    private Long studentId;
    private String studentName;
}
