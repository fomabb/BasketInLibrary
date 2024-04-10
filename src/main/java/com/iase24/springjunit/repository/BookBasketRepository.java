package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.BookBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookBasketRepository extends JpaRepository<BookBasket, Long> {

    Optional<BookBasket> findByBasketAndBook(Basket basket, Book book);
}
