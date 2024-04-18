package com.iase24.springjunit.repository;

import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.BookBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookBasketRepository extends JpaRepository<BookBasket, Long> {

    Optional<BookBasket> findByBasketAndBook(Basket basket, Book book);

    /**
     * Находим по корзине пользователя все книги в его корзине, проводим расчёт общей суммы за все товары в корзине
     *
     * @return float count price by all books
     */
    @Query("select sum(bb.priceQuantity) from BookBasket bb where bb.basket=:basket")
    float findBooksByBasket(Basket basket);
}
