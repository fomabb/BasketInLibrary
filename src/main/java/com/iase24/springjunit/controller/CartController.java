package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.BookAddCartDTO;
import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PutMapping("/cartId/{cartId}/bookId/{bookId}")
    public Cart addBookInCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
            ) {

        return cartService.addBookInCart(cartId, bookId);
    }

    @PostMapping
    public Cart addCart(@RequestBody Cart cart) {

        return cartService.addCart(cart);
    }

    @GetMapping
    public List<Cart> getCarts() {

        return cartService.getCarts();
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable("cartId") Long cartId) {

        return cartService.getCartById(cartId);
    }

    @PutMapping("/bookId/{bookId}")
    public BookUpdateDTO updateBookInCart(
            @PathVariable("bookId") Long bookId,
            @RequestBody BookUpdateDTO bookUpdateDTO
    ) {

        cartService.updateBookInCart(bookId, bookUpdateDTO);

        return BookUpdateDTO.builder().build();
    }
}
