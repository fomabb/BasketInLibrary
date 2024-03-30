package com.iase24.springjunit.component;

import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.DescriptionCategory;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class BookResponse {


    private List<Book> data;
    private List<DescriptionCategory> descriptionData;
    private PaginationInfo paginationInfo;
}
