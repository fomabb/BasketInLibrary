package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.repository.BasketRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.BasketService;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Override
    public Optional<Basket> getBasketById(Long id) {

        return basketRepository.findById(id);
    }

    @Override
    public void updateCountBookInBasket(Long id, BookUpdateDTO bookUpdateDTO) {

        Book book = bookRepository.findById(id).orElse(null);

        assert book != null;
        if (book.getBasket() != null && book.getStatus() == Status.ACTIVE) {
            book.setCount(bookUpdateDTO.getCount());
            if (book.getCount() < 1) {
                book.setBasket(null);
                book.setCount(1);
                book.setStatus(Status.ACTIVE);
            }
        }

        Book updateBook = bookRepository.save(book);
        new BookUpdateDTO(updateBook.getCount(), updateBook.getStatus());
    }
}
