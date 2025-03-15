package com.iase24.springjunit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime dateCreate;

    @Column(name = "all_price")
    private BigDecimal allPrice;

    @JsonBackReference("cart-user")
    @OneToOne(mappedBy = "cart")
    private User user;

    @OneToMany(mappedBy = "cart", orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<ProductCart> productsCarts = new ArrayList<>();
}
