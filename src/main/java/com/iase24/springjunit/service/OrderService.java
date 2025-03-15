package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.dto.request.MakingAnOrderDataDtoRequest;
import com.iase24.springjunit.entities.Order;

import java.util.List;

public interface OrderService {

    /**
     * Необходимо создать DTO для всех методов
     */

    Order addOrder(Order order);

    List<Order> getOrders();

    Order getOrderById(Long orderId);

    Order addProductInOrder(MakingAnOrderDataDtoRequest dto);

    void updateProductInOrder(Long bookId, ProductUpdateDTO productUpdateDTO);

    void removeFromOrder(Long orderId, Long bookId);

    Order getOrderByLogin(String username);
}
