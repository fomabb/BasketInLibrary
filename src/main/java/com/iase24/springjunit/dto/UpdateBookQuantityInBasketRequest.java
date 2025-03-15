package com.iase24.springjunit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookQuantityInBasketRequest {

    private Long cartId;
    private Long productId;
    private Integer quantity;
}
