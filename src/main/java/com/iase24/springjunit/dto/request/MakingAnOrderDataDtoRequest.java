package com.iase24.springjunit.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MakingAnOrderDataDtoRequest {

    private Long orderId;
    private Long productId;
}
