package com.iase24.springjunit.dto.request;

import com.iase24.springjunit.dto.ParentNodeDataDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookToCategoryDataDtoRequest {

    private Long bookId;
    private ParentNodeDataDto categoryId;
}
