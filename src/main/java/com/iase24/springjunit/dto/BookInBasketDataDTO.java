package com.iase24.springjunit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookInBasketDataDTO {
    private Long id;
    private String title;
    private int quantity;
}
