package com.app.bibliotecauniversitariapa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String enrollmentNumber; // matrícula

    private String career;   // carrera / programa académico
    private int semester;    // semestre actual
    private String phone;

    //paso 2
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Loan> loans;

    public void addLoan(Loan loan){
      loans.add(loan);
      loan.setStudent(this);
    }

    public void removeLoan(Loan loan){
        loans.remove(loan);
        loan.setStudent(null);
    }
}