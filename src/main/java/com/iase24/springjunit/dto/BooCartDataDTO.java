package com.iase24.springjunit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooCartDataDTO {

    private Long orderNumber;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime creationTime;

    private Book book;

}
