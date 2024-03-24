package com.iase24.springjunit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_test")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String login;

    private String email;

    private String password;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    @PostPersist
    public void onCreate() {

        if (cart == null) {
            cart = new Cart();
            cart.setDateTime(LocalDateTime.now());
        }
    }
}
