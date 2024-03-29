package com.iase24.springjunit.dto;

import com.iase24.springjunit.entities.Status;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookDataDTO {

    Long id;
    String title;
    String author;
    String genre;
    Status status;
    String publisher;
    int count;
}
