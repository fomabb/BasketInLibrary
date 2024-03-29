package com.iase24.springjunit.dto;

import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDataDTO {

    private Long id;

    private LocalDateTime dateTime;

    private List<Book> books;

    private User user;
}
