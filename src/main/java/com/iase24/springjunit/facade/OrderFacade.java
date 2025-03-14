package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.ProductOrderDataDTO;
import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.service.ProductOrderService;
import com.iase24.springjunit.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final ProductOrderService productOrderService;

    public Order addOrder(Order order) {
        return orderService.addOrder(order);
    }

    public List<ProductOrderDataDTO> getAllOrders(Long cartId) {
        return productOrderService.findAllByOrderId(cartId);
    }

    public ProductUpdateDTO updateProductInOrder(Long bookId, ProductUpdateDTO productUpdateDTO) {
        orderService.updateProductInOrder(bookId, productUpdateDTO);
        return ProductUpdateDTO.builder().build();
    }
}
