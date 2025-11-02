package com.app.bibliotecauniversitariapa.entities;

import com.app.bibliotecauniversitariapa.entities.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate loanDate;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @OneToOne(mappedBy = "loan")
    @JsonManagedReference
    private BookReturn bookReturn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "book_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_loan_book")
    )
    @JsonBackReference
    private Book book;
    //paso 1
    @ManyToOne(fetch = FetchType.LAZY, optional = false )
    @JoinColumn(name = "student_id",
            nullable = false,
            foreignKey = @ForeignKey(name="fk_loan_student"))
    @JsonBackReference
    private Student student;
}