package com.iase24.springjunit.mapper.cart;

import com.iase24.springjunit.dto.CartDataDTO;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.mapper.Mapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Component
public class CartMapper implements Mapper<Order, CartDataDTO> {

    private static final CartMapper INSTANCE = new CartMapper();

    public static CartMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public CartDataDTO map(Order object) {
        return CartDataDTO.builder()
                .id(object.getId())
                .dateTime(object.getDateTime())
                .products(object.getProducts())
                .user(object.getUser())
                .build();
    }
}
