package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookInBasketDataDTO;
import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;

import java.util.List;

public interface BasketService {
    Basket findBasketById(Long id);
    List<BookInBasketDataDTO> findBooksInBasketById(Long basketId);
}
