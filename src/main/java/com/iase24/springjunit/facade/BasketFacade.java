package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.BookInBasketDataDTO;
import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.exception.AppError;
import com.iase24.springjunit.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketFacade {

    private final BasketService basketService;

    public Basket getBasketById(Long id) {
        if (id == null) {
            new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), "Basket not found"), HttpStatus.NOT_FOUND
            );
        }
        return basketService.findBasketById(id);
    }

    public List<BookInBasketDataDTO> getBooksInBasketById(Long basketId) {
        return basketService.findBooksInBasketById(basketId);
    }
}