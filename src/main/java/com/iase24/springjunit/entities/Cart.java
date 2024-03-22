package com.iase24.springjunit.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateTime;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime getDateTime;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime returnDateTime;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "user_id")
//    private User user;

//    @ManyToMany(
//            fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//            CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinTable(name = "BOOK_CART_MAPPING", joinColumns = @JoinColumn(name = "cart_id"),
//            inverseJoinColumns = @JoinColumn(name = "book_id"))
//    private List<Book> books = new ArrayList<>();

    @ManyToMany(mappedBy = "cart")
    private List<Book> books = new ArrayList<>();
}
