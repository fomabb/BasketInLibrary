package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Basket;

import java.util.Optional;

public interface BasketService {
    Optional<Basket> getBasketById(Long id);

    void updateCountBookInBasket(Long id, BookUpdateDTO bookUpdateDTO) throws IllegalAccessException;
}
