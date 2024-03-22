package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.CartDataDTO;
import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.repository.BasketRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.service.BasketService;
import com.iase24.springjunit.service.BookService;
import com.iase24.springjunit.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final BookService bookService;
    private final BasketService basketService;
    private final BookRepository bookRepository;
    private final BasketRepository basketRepository;

    @Override
    public void addCart(Cart cart, Long bookId) {

        cartRepository.save(cart);

        Book book = bookRepository.findById(cart.getBook().getId()).orElse(null);

//        assert book != null;
//        bookService.updateBookCounter(book.getId(), book.getCount() - 1);
    }
}
