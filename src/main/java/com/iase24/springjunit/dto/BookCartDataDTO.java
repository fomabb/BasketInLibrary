package com.iase24.springjunit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iase24.springjunit.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCartDataDTO {

    private Long orderNumber;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime creationTime;

    private Book book;

}
