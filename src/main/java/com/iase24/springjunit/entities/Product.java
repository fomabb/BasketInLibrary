package com.iase24.springjunit.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

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

    @Column(name = "price")
    private float price;

    @Column(name = "delivery")
    private int delivery;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "count")
    private Integer count;

    @JsonBackReference("product-order")
    @ManyToMany(mappedBy = "products")
    private List<Order> orders = new ArrayList<>();

    @JsonBackReference("products-node")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", referencedColumnName = "id")
    private Node node;

    @JsonBackReference("product-bookBasket")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductCart> productCarts = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(genre, product.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(genre);
    }
}
