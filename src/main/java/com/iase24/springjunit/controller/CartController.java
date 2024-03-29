package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.BookCartDataDTO;
import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.service.BookCartService;
import com.iase24.springjunit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final BookCartService bookCartService;

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

    @DeleteMapping("cartId/{cartId}/bookId/{bookId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long cartId, @PathVariable Long bookId) {
        cartService.removeFromCart(cartId, bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{login}")
    public Cart getCartByUser(@PathVariable("login") String login) {

        return cartService.getCartByLogin(login);
    }

    @GetMapping("/allCarts/{cartId}")
    public List<BookCartDataDTO> getAllCarts(@PathVariable("cartId") Long cartId) {

        return bookCartService.findAllByCartId(cartId);
    }
}
