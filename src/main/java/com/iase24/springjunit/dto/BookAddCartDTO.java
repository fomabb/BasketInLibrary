package com.iase24.springjunit.dto;

import com.iase24.springjunit.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAddCartDTO {

    private Book book_id;
}
