package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.BookInBasketDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Product;
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

    public Cart getBasketById(Long id) {
        if (id == null) {
            new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), "Cart not found"), HttpStatus.NOT_FOUND
            );
        }
        return basketService.findBasketById(id);
    }

    public List<BookInBasketDataDTO> getBooksInBasketById(Long basketId) {
        return basketService.findBooksInBasketById(basketId);
    }

    public Cart createBasket(Long basketId, Long bookId) {
        return basketService.addBookInBasket(basketId, bookId);
    }

    public UpdateBookQuantityInBasket updateQuantity(Long basketId, Long bookId, UpdateBookQuantityInBasket updateBookQuantity) {
        return basketService.updateQuantityInBasket(basketId, bookId, updateBookQuantity);
    }

    public ResponseEntity<?> removeBookInBasket(Long basketId, Long bookId) {
        basketService.removeBookInBasket(basketId, bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Product> toDoOrdersInBasketByQuantity(Long basketId, Long quantity) {
        basketService.toDoOrdersInBasketByQuantity(basketId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}