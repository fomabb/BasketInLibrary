package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.ProductOrderDataDTO;
import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.facade.OrderFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "API для управления заказами")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class OrderController {

    private final OrderFacade orderFacade;

    @PostMapping
    public Order addOrder(@RequestBody Order order) {
        return orderFacade.addOrder(order);
    }

    /**
     * Показать все заказы
     *
     * @return orders
     */
    @GetMapping("/allOrders/{orderId}")
    public List<ProductOrderDataDTO> getAllOrders(@PathVariable("orderId") Long orderId) {
        return orderFacade.getAllOrders(orderId);
    }

    @PutMapping("/productId/{productId}")
    public ProductUpdateDTO updateBookInCart(
            @PathVariable("productId") Long productId,
            @RequestBody ProductUpdateDTO productUpdateDTO
    ) {
        return orderFacade.updateProductInOrder(productId, productUpdateDTO);
    }
}
