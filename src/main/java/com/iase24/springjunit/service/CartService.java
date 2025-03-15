package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.ProductInCartDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.dto.request.CartProductDataDtoRequest;
import com.iase24.springjunit.dto.request.OrdersInTheCartByQuantityDataDtoRequest;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Product;

import java.util.List;

public interface CartService {
    Cart findCartById(Long id);

    List<ProductInCartDataDTO> findProductInCartById(Long cartId);

    Cart addProductInCart(CartProductDataDtoRequest dataDtoRequest);

    UpdateBookQuantityInBasket updateQuantityInCart(Long cartId, Long productId, UpdateBookQuantityInBasket updateQuantity);

    void removeProductInCart(Long cartId, Long productId);

    Product toDoOrdersInCartByQuantity(OrdersInTheCartByQuantityDataDtoRequest dataDtoRequest);
}
