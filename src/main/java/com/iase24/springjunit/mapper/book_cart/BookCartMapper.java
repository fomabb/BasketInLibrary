package com.iase24.springjunit.mapper.book_cart;

import com.iase24.springjunit.dto.BookCartDataDTO;
import com.iase24.springjunit.entities.BookCart;
import com.iase24.springjunit.mapper.Mapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@NoArgsConstructor(access = PRIVATE)
public class BookCartMapper implements Mapper<BookCart, BookCartDataDTO> {

    private static final BookCartMapper INSTANCE = new BookCartMapper();

    public static BookCartMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public BookCartDataDTO map(BookCart object) {
        return BookCartDataDTO.builder()
                .orderNumber(object.getId())
                .creationTime(object.getCreationTime())
                .book(object.getBook())
                .build();
    }
}
