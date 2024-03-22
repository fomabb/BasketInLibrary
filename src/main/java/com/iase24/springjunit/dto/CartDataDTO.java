package com.iase24.springjunit.dto;

import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDataDTO {

    private Book book_id;
    private Basket basket_id;
}
