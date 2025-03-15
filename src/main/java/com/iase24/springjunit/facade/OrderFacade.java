package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;

    public ProductUpdateDTO updateProductInOrder(Long bookId, ProductUpdateDTO productUpdateDTO) {
        orderService.updateProductInOrder(bookId, productUpdateDTO);
        return ProductUpdateDTO.builder().build();
    }

    public ResponseEntity<?> removeFromCart(Long cartId, Long bookId) {
        orderService.removeFromOrder(cartId, bookId);
        return new ResponseEntity<>(
                "Product with id " + bookId + " remove in order with id " + cartId
                , HttpStatus.OK
        );
    }
}
