package com.iase24.springjunit.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductQuantityDataDtoRequest {

    private Long productId;
    private int quantity;
}
