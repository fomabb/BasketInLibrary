package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Cart;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    /**
     *Необходимо создать DTO для всех методв
     */

    Cart addCart(Cart cart);

    List<Cart> getCarts();

    Cart getCartById(Long cartId);

    Cart addBookInCart(Long cartId, Long bookId);

    void updateBookInCart(Long bookId, BookUpdateDTO bookUpdateDTO);

    ResponseEntity<?> removeFromCart(Long cartId, Long bookId);

    Cart getCartByLogin(String username);
}
