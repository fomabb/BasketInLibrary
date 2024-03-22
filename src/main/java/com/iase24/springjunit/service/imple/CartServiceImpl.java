package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.service.UserService;
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
    private final UserService userService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public void addCart(Cart cart) {


        cartRepository.save(cart);

//        Book book = bookRepository.findById(cart.getBook().getBook_id()).orElse(null);
//
//        assert book != null;
//        bookService.updateBookCounter(book.getId(), book.getCount() - 1);
    }
}
