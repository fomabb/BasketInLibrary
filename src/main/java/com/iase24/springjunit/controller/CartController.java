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

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final BookCartService bookCartService;

    @PostMapping
    public Cart addCart(@RequestBody Cart cart) {

        return cartService.addCart(cart);
    }

    @GetMapping("/allCarts/{cartId}")
    public List<BookCartDataDTO> getAllCarts(@PathVariable("cartId") Long cartId) {

        return bookCartService.findAllByCartId(cartId);
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
