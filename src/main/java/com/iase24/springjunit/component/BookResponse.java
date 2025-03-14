package com.iase24.springjunit.component;

import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class BookResponse {


    private List<Product> data;
    private List<DescriptionCategory> descriptionData;
    private PaginationInfo paginationInfo;
}
