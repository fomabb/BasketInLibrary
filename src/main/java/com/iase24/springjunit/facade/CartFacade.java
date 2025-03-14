package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.ProductInCartDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.exception.AppError;
import com.iase24.springjunit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartFacade {

    private final CartService cartService;

    public Cart getCartById(Long id) {
        if (id == null) {
            new ResponseEntity<>(
                    new AppError(HttpStatus.NOT_FOUND.value(), "Cart not found"), HttpStatus.NOT_FOUND
            );
        }
        return cartService.findCartById(id);
    }

    public List<ProductInCartDataDTO> getProductsInCartById(Long cartId) {
        return cartService.findProductInCartById(cartId);
    }

    public Cart createCart(Long cartId, Long productId) {
        return cartService.addProductInCart(cartId, productId);
    }

    public UpdateBookQuantityInBasket updateQuantity(Long cartId, Long productId, UpdateBookQuantityInBasket updateBookQuantity) {
        return cartService.updateQuantityInCart(cartId, productId, updateBookQuantity);
    }

    public ResponseEntity<?> removeProductInCart(Long cartId, Long productId) {
        cartService.removeProductInCart(cartId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Product> toDoOrdersInCartByQuantity(Long cartId, Long quantity) {
        cartService.toDoOrdersInCartByQuantity(cartId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}