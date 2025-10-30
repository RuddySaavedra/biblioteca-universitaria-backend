package com.app.bibliotecauniversitariapa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subject;

    @Column(unique = true, nullable = false)
    private String isbn;

    private int publicationYear;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true //si borro el libro el prestamo no existe
    )
    @JsonManagedReference
    private List<Loan> loans = new ArrayList<>();//solo para many to one o OneToMany

    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setBook(this);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
        loan.setBook(null);
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name="fk_book_author"))
    @JsonBackReference
    private Author author;
}