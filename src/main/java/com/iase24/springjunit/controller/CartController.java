package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.CartDataDTO;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cart/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PutMapping
    public void addCart(@RequestBody Cart cart) {

        cartService.addCart(cart);
    }
}
