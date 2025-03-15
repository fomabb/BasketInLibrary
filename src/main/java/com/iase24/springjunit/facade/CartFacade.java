package com.iase24.springjunit.facade;

import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.exception.AppError;
import com.iase24.springjunit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartFacade {

    private final CartService cartService;

    public Cart getCartById(Long id) {
        if (id == null) {
            new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), "Cart not found"), HttpStatus.NOT_FOUND
            );
        }
        return cartService.findCartById(id);
    }
}