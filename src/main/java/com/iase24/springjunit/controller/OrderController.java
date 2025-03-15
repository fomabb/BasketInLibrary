package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.ProductOrderDataDTO;
import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.dto.request.MakingAnOrderDataDtoRequest;
import com.iase24.springjunit.dto.request.OrdersInTheCartByQuantityDataDtoRequest;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.ProductOrder;
import com.iase24.springjunit.facade.OrderFacade;
import com.iase24.springjunit.service.CartService;
import com.iase24.springjunit.service.OrderService;
import com.iase24.springjunit.service.ProductOrderService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "API для управления заказами")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class OrderController {

    private final OrderFacade orderFacade;
    private final OrderService orderService;
    private final ProductOrderService productOrderService;
    private final CartService cartService;

    @Hidden
    @PostMapping("/add-order")
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    /**
     * Показать все заказы
     *
     * @return orders
     */
    @Operation(
            summary = "Показать все заказы по ID карты",
            description = """
                    ```Необходимо в путь вставить ID корзины```
                    """
    )
    @GetMapping("/allOrders/{orderId}")
    public ResponseEntity<List<ProductOrderDataDTO>> getAllOrders(@PathVariable("orderId") Long cartId) {
        return ResponseEntity.ok(productOrderService.findAllByOrderId(cartId));
    }

    @Operation(
            summary = "```Обновить продукт в заказе```",
            description = """
                    [ПЕРЕСМОТРЕТЬ ЧТО ИМЕННО ОБНОВЛЯТЬ]
                    """
    )
    @PutMapping("/productId/{productId}")
    public ResponseEntity<ProductUpdateDTO> updateBookInOrder(
            @PathVariable("productId") Long productId,
            @RequestBody ProductUpdateDTO productUpdateDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderFacade.updateProductInOrder(productId, productUpdateDTO));
    }

    /**
     * Оформление заказа по ID товара
     */
    @Operation(
            summary = "Оформление заказа вне корзины пользователя.",
            description = """
                    ```Необходимо в теле запроса указать ID заказа созданного вместе с пользователем.```
                    """
    )
    @PutMapping("/making-an-order")
    public ResponseEntity<Order> addBookInOrder(@RequestBody MakingAnOrderDataDtoRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.addProductInOrder(request));
    }

    /**
     * Найти заказ по ID
     *
     * @return order
     */
    @Operation(
            summary = "Найти заказ по его ID",
            description = """
                    ```В путь прописать ID заказа созданного вместе с пользователем.```
                    """
    )
    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") Long orderId) {

        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    /**
     * Отменить сформированный заказ
     */
    @Operation(
            summary = "Отменить заказ",
            description = """
                    ```В путь прописать IDs заказа созданного вместе с пользователем и продукта.```
                    """
    )
    @DeleteMapping("orderId/{orderId}/bookId/{bookId}")
    public ResponseEntity<?> removeFromOrder(
            @PathVariable Long orderId,
            @PathVariable Long bookId
    ) {
        return ResponseEntity.accepted().body(orderFacade.removeFromCart(orderId, bookId));
    }

    @Operation(
            summary = "Найдите отчет о доставке по идентификатору заказа.",
            description = """
                    ```В путь прописать ID заказа созданного вместе с пользователем.```
                    """
    )
    @GetMapping("/show/report/orderId/{orderId}")
    public ResponseEntity<List<ProductOrder>> findDeliveryReportByOrderId(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(productOrderService.findDeliveryReportByOrderId(orderId));
    }

    @Operation(
            summary = "Поиск архивных заказов по идентификатору заказа.",
            description = """
                    ```В путь прописать ID заказа созданного вместе с пользователем.```
                    """
    )
    @GetMapping("/show/archive/orders/orderId/{orderId}")
    public ResponseEntity<List<ProductOrder>> findArchiveOrdersByOrderId(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(productOrderService.findArchiveOrdersByCartId(orderId));
    }

    /**
     * Сформировать заказ по количеству товара
     *
     * @return product
     */
    @Operation(
            summary = "Сформировать заказ по количеству товара.",
            description = """
                    ```В теле запроса необходимо указать IDs корзины заказов и продукта.```
                    """
    )
    @PostMapping("/createOrdersByQuantityInCart")
    public ResponseEntity<Product> toDoOrdersInBasketByQuantity(
            @RequestBody OrdersInTheCartByQuantityDataDtoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.toDoOrdersInCartByQuantity(request));
    }
}
