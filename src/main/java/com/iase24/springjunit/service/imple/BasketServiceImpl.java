package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookInBasketDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.entities.*;
import com.iase24.springjunit.repository.BasketRepository;
import com.iase24.springjunit.repository.BookBasketRepository;
import com.iase24.springjunit.repository.BookCartRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.BasketService;
import com.iase24.springjunit.service.BookService;
import com.iase24.springjunit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketServiceImpl implements BasketService {

    private final BookBasketRepository bookBasketRepository;
    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final CartService cartService;
    private final BookCartRepository bookCartRepository;

    @Override
    public Basket findBasketById(Long id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Basket with id: %s not found", id)));
    }

    @Override
    public List<BookInBasketDataDTO> findBooksInBasketById(Long basketId) {
        return bookRepository.findBooksByBookBasketsId(basketId)
                .stream()
                .map(book -> new BookInBasketDataDTO(book.getId(), book.getTitle(), book.getCount())).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Basket addBookInBasket(Long basketId, Long bookId) {
        Basket basket = findBasketById(basketId);
        Book book = bookService.getBookById(bookId);

        // Проверяем, есть ли уже книга в корзине
        Optional<BookBasket> existingBookBasket = bookBasketRepository.findByBasketAndBook(basket, book);
        if (existingBookBasket.isPresent()) {
            throw new IllegalArgumentException("Book is already in the basket");
        }
        if (book.getCount() > 0) {
            bookRepository.save(book);
            BookBasket bookBasket = new BookBasket();
            bookBasket.setBook(book);
            bookBasket.setBasket(basket);
            bookBasket.setQuantity(1);
            bookBasketRepository.save(bookBasket);
            return basket;
        } else {
            throw new IllegalArgumentException("Basket count exceeded");
        }
    }

    @Transactional
    @Override
    public UpdateBookQuantityInBasket updateQuantityInBasket(
            Long basketId, Long bookId, UpdateBookQuantityInBasket updateBookQuantity
    ) {
        Basket basket = findBasketById(basketId);
        Book book = bookService.getBookById(bookId);

        // Найти существующий BookBasket для данной корзины и книги
        BookBasket bookBasket = bookBasketRepository.findByBasketAndBook(basket, book)
                .orElseThrow(() -> new RuntimeException("BookBasket not found"));

        // Проверить, не превышает ли новое количество доступное количество книги ии проверяем не меньше ли доступного
        if (updateBookQuantity.getQuantity() <= book.getCount() && updateBookQuantity.getQuantity() > 0) {
            // Обновить количество в существующем BookBasket
            bookBasket.setQuantity(updateBookQuantity.getQuantity());
            bookBasketRepository.save(bookBasket);
            return new UpdateBookQuantityInBasket(bookBasket.getQuantity());
        } else {
            throw new IllegalArgumentException("Book count exceeded");
        }
    }

    @Transactional
    @Override
    public void removeBookInBasket(Long basketId, Long bookId) {
        Book book = bookService.getBookById(bookId);
        Basket basket = findBasketById(basketId);
        BookBasket bookBasket = bookBasketRepository.findByBasketAndBook(basket, book)
                .orElseThrow(() -> new RuntimeException("BookBasket not found"));
        basket.getBookBaskets().remove(bookBasket);
        if (!basket.getBookBaskets().contains(bookBasket)) {
            basketRepository.save(basket);
        } else {
            throw new IllegalArgumentException("Delete book failed");
        }
    }

    @Transactional
    @Override
    public void toDoOrdersInBasketByQuantity(Long basketId, Long bookId) {
        Basket basket = findBasketById(basketId);
        Cart cart = cartService.getCartById(basketId);
        Book book = bookService.getBookById(bookId);
        BookBasket bookBasket = bookBasketRepository.findByBasketAndBook(basket, book)
                .orElseThrow(() -> new RuntimeException("BookBasket not found")
                );

        // список всех заказов для сохранения в базу данных
        List<BookCart> allOrdersByQuantity = new ArrayList<>();
        for (int i = 0; i < bookBasket.getQuantity(); i++) {
            BookCart bookCart = new BookCart();
            book.setCount(book.getCount() - 1);
            bookCart.setBook(book);
            bookCart.setCart(cart);
            bookCart.setCreationTime(LocalDateTime.now());
            allOrdersByQuantity.add(bookCart);
        }
        bookBasket.setQuantity(book.getCount()/bookBasket.getQuantity());
        bookCartRepository.saveAll(allOrdersByQuantity);
        if (book.getCount() <= 0) {
            book.setStatus(Status.INACTIVE);
        }

        // удаление книги из корзины если, колличество меньше 1-го
        if (bookBasket.getQuantity() <= 0) {
            removeBookInBasket(basketId, book.getId());
        }
    }
}
