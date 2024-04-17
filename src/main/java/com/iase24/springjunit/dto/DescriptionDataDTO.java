package com.iase24.springjunit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionDataDTO {
    private Long id;
    private String title;
    private String category;
    private String description;
}
