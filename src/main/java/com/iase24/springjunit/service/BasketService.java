package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookInBasketDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.BookBasket;

import java.util.List;

public interface BasketService {
    Basket findBasketById(Long id);

    List<BookInBasketDataDTO> findBooksInBasketById(Long basketId);

    Basket addBookInBasket(Long basketId, Long bookId);

    UpdateBookQuantityInBasket updateQuantityInBasket(Long basketId, Long bookId, UpdateBookQuantityInBasket updateQuantity);

    void removeBookInBasket(Long basketId, Long bookId);

    void toDoOrdersInBasketByQuantity(Long basketId, Long bookId);
}
