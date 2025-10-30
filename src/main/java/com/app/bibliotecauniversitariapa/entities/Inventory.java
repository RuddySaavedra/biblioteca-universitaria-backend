package com.app.bibliotecauniversitariapa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalCopies;
    private int availableCopies;
    private int minThreshold;
    @OneToOne(mappedBy = "inventory", cascade = CascadeType.ALL, optional = false)
    private Book book;
    public void setBook(Book book) {
        this.book = book;
        if (book != null) {
            book.setInventory(this);
        }
    }
}
