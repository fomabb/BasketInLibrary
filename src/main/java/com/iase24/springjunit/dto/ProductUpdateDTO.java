package com.iase24.springjunit.dto;

import com.iase24.springjunit.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateDTO {
    private Long id;
    private int count;
    private Status status;
}
