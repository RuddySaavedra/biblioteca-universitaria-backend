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
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
//variable type, solo A,B,C
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String type;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", unique = true, foreignKey = @ForeignKey(name = "fk_author_address"))
    @JsonBackReference
    private Address address;

    public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.setAuthor(this);
        }
    }
}