package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookInBasketDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.BookBasket;
import com.iase24.springjunit.repository.BasketRepository;
import com.iase24.springjunit.repository.BookBasketRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.BasketService;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
