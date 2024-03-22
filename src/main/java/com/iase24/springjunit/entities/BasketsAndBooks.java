//package com.iase24.springjunit.entities;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "baskets_books")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class BasketsAndBooks {
//
//    @Id
//    private Long id;
//
//    @OneToMany(mappedBy = "book"
//            , cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
//            , fetch = FetchType.LAZY)
//    private List<Book> books = new ArrayList<>();
//
//    @OneToMany(mappedBy = "basket"
//            , cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
//            , fetch = FetchType.LAZY)
//    private List<Basket> baskets = new ArrayList<>();
//}
