package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.BookCart;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.entities.enumerated.DeliveryReport;
import com.iase24.springjunit.repository.BookCartRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.service.BookService;
import com.iase24.springjunit.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final BookCartRepository bookCartRepository;

    @Override
    @Transactional
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
                    userDataDTO.setUsername(cart.getUser().getUsername());
                    userDataDTO.setEmail(cart.getUser().getEmail());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Cart with id " + cartId + " not found"));
    }

    @Override
    @Transactional
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

    /**
     * Метод добавляющий книгу в картачку заказов пользователя
     */
    @Override
    @Transactional
    public Cart addBookInCart(Long cartId, Long bookId) {
        Cart cart = getCartById(cartId);
        Book book = bookService.getBookById(bookId);
        if (book.getCount() > 0) {

            // Уменьшаем количество книги на складе
            book.setCount(book.getCount() - 1);

            // Сохраняем изменения в книге
            bookRepository.save(book);
            if (book.getCount() <= 0) {
                book.setStatus(Status.INACTIVE);
            }
            BookCart bookCart = new BookCart();
            bookCart.setBook(book);
            bookCart.setCart(cart);
            bookCart.setCreationTime(LocalDateTime.now());
            bookCart.setDeliveryReport(DeliveryReport.HALFWAY_THROUGH);
            bookCartRepository.saveAndFlush(bookCart);
            return cart;
        } else {
            throw new IllegalArgumentException("Book with id " + bookId + " not found");
        }
    }

    //TODO: необходимо изменить логику, для того, чтобы не изменялся ID и LocalDateTime
    @Override
    @Transactional
    public void removeFromCart(Long cartId, Long bookId) {
        Cart cart = getCartById(cartId);

        //TODO: в процессе изменения
        // сохранение изначального ID и даты====================
        Long originCartId = cart.getId();
        LocalDateTime originCreationTime = cart.getDateTime();
        //======================================================

        Book bookToRemove = bookService.getBookById(bookId);

        // Проверяем, была ли книга в корзине до удаления
        boolean wasInCart = cart.getBooks().contains(bookToRemove);

        // Удаление книги из корзины
        if (cart.getBooks().remove(bookToRemove)) {
            cart.setId(originCartId);
            cart.setDateTime(originCreationTime);
            cartRepository.save(cart);

            // Проверка, остались ли еще книги в карте
            if (cart.getBooks().isEmpty()) {

                // Если карта стала пустой, но книга была в ней до удаления,
                // возвращаем книгу на склад
                if (wasInCart) {
                    returnBookToStock(bookToRemove.getId());
                }
            } else {

                // Если в карте еще остались книги, возвращаем книгу на склад
                returnBookToStock(bookToRemove.getId());
            }
        } else {
            throw new EntityNotFoundException("Book with id " + bookId + " not found");
        }
    }

    /**
     * Метод добавляющий книгу на склад после удаления из заказов
     */
    public void returnBookToStock(Long bookId) {
        Book book = bookService.getBookById(bookId);
        book.setCount(book.getCount() + 1);
        if (book.getCount() > 0) {
            book.setStatus(Status.ACTIVE);
        }
        bookRepository.save(book);
    }

    @Override
    public Cart getCartByLogin(String username) {
        return cartRepository.findCartByUser_Username(username)
                .orElseThrow(() -> new EntityNotFoundException("User with name: " + username + " not found"));
    }
}
