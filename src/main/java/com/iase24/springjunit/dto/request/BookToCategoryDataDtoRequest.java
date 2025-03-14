package com.iase24.springjunit.dto.request;

import com.iase24.springjunit.entities.Node;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookToCategoryDataDtoRequest {

    private Long bookId;
    private Node categoryId;
}
