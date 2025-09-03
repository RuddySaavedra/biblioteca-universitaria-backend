package com.app.bibliotecauniversitariapa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @Column(length = 10, unique = true, nullable = false)
    private String registrationNumber;

    private String firstName;
    private String lastName;
    private String career;
    private String phone;
}
