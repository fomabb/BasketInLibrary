package com.iase24.springjunit.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrdersInTheCartByQuantityDataDtoRequest {

    private Long cartId;
    private Long productId;
}
