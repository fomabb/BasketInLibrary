package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Order;

import java.util.List;

public interface CartService {

    /**
     *Необходимо создать DTO для всех методв
     */

    Order addCart(Order order);

    List<Order> getCarts();

    Order getCartById(Long cartId);

    Order addBookInCart(Long cartId, Long bookId);

    void updateBookInCart(Long bookId, BookUpdateDTO bookUpdateDTO);

    void removeFromCart(Long cartId, Long bookId);

    Order getCartByLogin(String username);
}
