package com.app.bibliotecauniversitariapa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    // Dejamos el FetchType Eager por defecto porque siempre vamos a necesitar los datos del libro.
    @OneToOne
    @JoinColumn(name = "book_id", unique = true, foreignKey = @ForeignKey(name = "fk_inventory_book"))
    @JsonBackReference // Indica quién es el lado dueño de la relación
    private Book book;
}
