package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.service.BookService;
import com.iase24.springjunit.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;

    @Override
    public Cart addCart(Cart cart) {

        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCarts() {

        List<Cart> carts = cartRepository.findAll();

        return carts.stream()
                .peek(cart -> {
                    UserDataDTO userDataDTO = new UserDataDTO();
                    userDataDTO.setId(cart.getUser().getId());
                    userDataDTO.setLogin(cart.getUser().getLogin());
                    userDataDTO.setEmail(cart.getUser().getEmail());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Cart getCartById(Long cartId) {

        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isPresent()) {

            return optionalCart.get();
        }
        throw new IllegalArgumentException("Cart with id " + cartId + " not found");
    }

    @Override
    public Cart addBookInCart(Long cartId, Long bookId) {
        //1
        Cart cart = getCartById(cartId);
        Book book = bookService.getBookById(bookId);

        if (book.getCount() > 0) {
            // Уменьшаем количество книги на складе
            book.setCount(book.getCount() - 1);
            bookRepository.save(book);

            if (book.getCount() <= 0) {
                book.setStatus(Status.INACTIVE);
            }

            cart.setPutDateTime(LocalDateTime.now());
            cart.getBooks().add(book);
            return cartRepository.save(cart);
        } else {
            throw new EntityNotFoundException("Book not exist");
        }
    }

    @Override
    public void updateBookInCart(Long bookId, BookUpdateDTO bookUpdateDTO) {

        Book book = bookService.getBookById(bookId);

        if (book.getId() != null) {

            if (book.getCount() <= 0) {
                book.setCount(bookUpdateDTO.getCount());
            }

            Book updateCount = bookRepository.save(book);
            new BookUpdateDTO(updateCount.getCount(), updateCount.getStatus());
        } else {
            throw new IllegalArgumentException("Book with id " + bookId + " not found");
        }
    }

    @Override
    public void removeFromCart(Long cartId, Long bookId) {
        Cart cart = getCartById(cartId);
        Book bookToRemove = bookService.getBookById(bookId);

        // Удаление книги из корзины
        if (cart.getBooks().remove(bookToRemove)) {
            cartRepository.save(cart);
            // Проверка, остались ли еще книги в корзине
            if (!cart.getBooks().isEmpty()) {
                // Возврат книги на склад, только если корзина не пуста
                returnBookToStock(bookToRemove.getId());
            }
        } else {
            throw new EntityNotFoundException("Book is Not in Cart");
        }
    }

    private void returnBookToStock(Long bookId) {
        Book book = bookService.getBookById(bookId);
        book.setCount(book.getCount() + 1);

        if (book.getCount() > 0) {
            book.setStatus(Status.ACTIVE);
        }

        bookRepository.save(book);
    }

    @Override
    public Cart getCartByLogin(String login) {

        Optional<Cart> cartOptional = cartRepository.findByUserLogin(login);

        if (cartOptional.isPresent()) {
            return cartOptional.get();
        } else {
            throw new EntityNotFoundException("User with login: " + login + " not found");
        }
    }
}
