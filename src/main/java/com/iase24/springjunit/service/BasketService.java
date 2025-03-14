package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookInBasketDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.entities.Cart;

import java.util.List;

public interface BasketService {
    Cart findBasketById(Long id);

    List<BookInBasketDataDTO> findBooksInBasketById(Long basketId);

    Cart addBookInBasket(Long basketId, Long bookId);

    UpdateBookQuantityInBasket updateQuantityInBasket(Long basketId, Long bookId, UpdateBookQuantityInBasket updateQuantity);

    void removeBookInBasket(Long basketId, Long bookId);

    void toDoOrdersInBasketByQuantity(Long basketId, Long bookId);
}
