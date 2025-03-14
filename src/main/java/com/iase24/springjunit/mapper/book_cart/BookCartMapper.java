package com.iase24.springjunit.mapper.book_cart;

import com.iase24.springjunit.dto.ProductOrderDataDTO;
import com.iase24.springjunit.entities.ProductOrder;
import com.iase24.springjunit.mapper.Mapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@NoArgsConstructor(access = PRIVATE)
public class BookCartMapper implements Mapper<ProductOrder, ProductOrderDataDTO> {

    private static final BookCartMapper INSTANCE = new BookCartMapper();

    public static BookCartMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ProductOrderDataDTO map(ProductOrder object) {
        return ProductOrderDataDTO.builder()
                .orderNumber(object.getId())
                .creationTime(object.getCreationTime())
                .product(object.getProduct())
                .build();
    }
}
