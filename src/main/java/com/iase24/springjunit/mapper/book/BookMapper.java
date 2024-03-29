package com.iase24.springjunit.mapper.book;

import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.mapper.Mapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@NoArgsConstructor(access = PRIVATE)
public class BookMapper implements Mapper<Book, BookDataDTO> {

    private static final BookMapper INSTANCE = new BookMapper();

    public static BookMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public BookDataDTO map(Book object) {
        return BookDataDTO.builder()
                .id(object.getId())
                .title(object.getTitle())
                .genre(object.getGenre())
                .author(object.getAuthor())
                .status(object.getStatus())
                .publisher(object.getPublisher())
                .count(object.getCount())
                .build();
    }
}
