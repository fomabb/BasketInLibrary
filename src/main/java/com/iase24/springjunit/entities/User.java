package com.iase24.springjunit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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

    @Email(message = "Email field does not match the request",
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,5}",
            flags = Pattern.Flag.UNICODE_CASE)
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
