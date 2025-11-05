package com.app.bibliotecauniversitariapa.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    // Atributos: Condominio, departamento, calle, piso, nro
    private String condominium;
    private String apartment;
    private String street;
    private String floor;

    @OneToOne(mappedBy = "address")
    @JsonManagedReference
    private Author author;
}
