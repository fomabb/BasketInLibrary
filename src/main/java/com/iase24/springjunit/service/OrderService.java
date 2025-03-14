package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.entities.Order;

import java.util.List;

public interface OrderService {

    /**
     *Необходимо создать DTO для всех методв
     */

    Order addOrder(Order order);

    List<Order> getOrders();

    Order getOrderById(Long cartId);

    Order addProductInOrder(Long cartId, Long bookId);

    void updateProductInOrder(Long bookId, ProductUpdateDTO productUpdateDTO);

    void removeFromOrder(Long cartId, Long bookId);

    Order getOrderByLogin(String username);
}
