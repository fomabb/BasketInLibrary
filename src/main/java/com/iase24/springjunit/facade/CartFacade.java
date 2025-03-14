package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.BookCartDataDTO;
import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.service.BookCartService;
import com.iase24.springjunit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartFacade {

    private final CartService cartService;
    private final BookCartService bookCartService;

    public Order addCart(Order order) {
        return cartService.addCart(order);
    }

    public List<BookCartDataDTO> getAllCarts(Long cartId) {
        return bookCartService.findAllByCartId(cartId);
    }

    public BookUpdateDTO updateBookInCart(Long bookId, BookUpdateDTO bookUpdateDTO) {
        cartService.updateBookInCart(bookId, bookUpdateDTO);
        return BookUpdateDTO.builder().build();
    }
}
