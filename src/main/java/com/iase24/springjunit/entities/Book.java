package com.iase24.springjunit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "count")
    private int count;

    @JsonBackReference("book-cart")
    @ManyToMany(mappedBy = "books")
    private List<Cart> carts = new ArrayList<>();

    @JsonBackReference("books-node")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", referencedColumnName = "id")
    private Node node;

    @JsonBackReference("book-bookBasket")
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookBasket> bookBaskets = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(genre);
    }
}
