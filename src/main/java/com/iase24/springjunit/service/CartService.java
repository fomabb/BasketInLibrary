package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.ProductInCartDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.entities.Cart;

import java.util.List;

public interface CartService {
    Cart findCartById(Long id);

    List<ProductInCartDataDTO> findProductInCartById(Long cartId);

    Cart addProductInCart(Long basketId, Long productId);

    UpdateBookQuantityInBasket updateQuantityInCart(Long cartId, Long productId, UpdateBookQuantityInBasket updateQuantity);

    void removeProductInCart(Long cartId, Long productId);

    void toDoOrdersInCartByQuantity(Long cartId, Long productId);
}
