package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.BookCartDataDTO;
import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.facade.CartFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartFacade cartFacade;

    @PostMapping
    public Order addCart(@RequestBody Order order) {
        return cartFacade.addCart(order);
    }

    /**
     * Показать все заказы
     *
     * @return orders
     */
    @GetMapping("/allCarts/{cartId}")
    public List<BookCartDataDTO> getAllCarts(@PathVariable("cartId") Long cartId) {
        return cartFacade.getAllCarts(cartId);
    }

    @PutMapping("/bookId/{bookId}")
    public BookUpdateDTO updateBookInCart(
            @PathVariable("bookId") Long bookId,
            @RequestBody BookUpdateDTO bookUpdateDTO
    ) {
        return cartFacade.updateBookInCart(bookId, bookUpdateDTO);
    }
}
