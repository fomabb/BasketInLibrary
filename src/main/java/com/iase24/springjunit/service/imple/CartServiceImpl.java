package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.service.UserService;
import com.iase24.springjunit.service.BookService;
import com.iase24.springjunit.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public Cart addCart(Cart cart) {

        return cartRepository.save(cart);


//        Book book = bookRepository.findById(cart.getBook().getBook_id()).orElse(null);
//
//        assert book != null;
//        bookService.updateBookCounter(book.getId(), book.getCount() - 1);
    }

    @Override
    public List<Cart> getCarts() {

        return cartRepository.findAll();
    }

    @Override
    public Cart getCartById(Long cartId) {

        return cartRepository.findById(cartId).orElse(null);
    }

    @Override
    public Cart addBookInCart(Long cartId, Long bookId) {

        List<Book> books = null;
        Cart cart = getCartById(cartId);
        Book book = bookService.getBookById(bookId).orElse(null);
        cart.setPutDateTime(LocalDateTime.now());

        books = cart.getBooks();
        books.add(book);
        cart.setBooks(books);
        return cartRepository.save(cart);
    }

    @Override
    public void updateBookInCart(Long bookId, BookUpdateDTO bookUpdateDTO) {
        Book book = bookService.getBookById(bookId).orElse(null);

        assert book != null;
        book.setCount(bookUpdateDTO.getCount());

        if (book.getCount() <= 0) {
            book.setCarts(null);
        }

        Book updateCount = bookRepository.save(book);
        new BookUpdateDTO(updateCount.getCount(), updateCount.getStatus());

    }
}
