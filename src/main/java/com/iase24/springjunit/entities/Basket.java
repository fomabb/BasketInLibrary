package com.iase24.springjunit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "basket")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateCreate;

    @OneToOne(mappedBy = "basket")
    private User user;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "book_basket",
//            joinColumns = @JoinColumn(name = "basket_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id"))
//    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "basket")
    private List<BookBasket> bookBaskets = new ArrayList<>();
}
