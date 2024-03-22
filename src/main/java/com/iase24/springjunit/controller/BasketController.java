package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping( value = "/api/basket/")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/this/{id}")
    public Optional<Basket> getBasketById(@PathVariable("id") Long id) {

        return basketService.getBasketById(id);
    }

    @PutMapping("/updateBasket/{id}")
    public BookUpdateDTO updateCountBookInBasket(
            @PathVariable("id") Long id,
            @RequestBody BookUpdateDTO bookUpdateDTO
    ) throws IllegalAccessException {

        if (id != null) {
            basketService.updateCountBookInBasket(id, bookUpdateDTO);
        }

        return bookUpdateDTO;
    }
}
