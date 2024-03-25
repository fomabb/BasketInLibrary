package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImplTest.class);

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    void getAllByGenre() {
        List<Book> books = getBooks();
        String genreAction = "Action";

        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllByGenre(genreAction);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(books.get(0), result.get(0));
    }

    @Test
    void createNewBook() {
        List<Book> book = getBooks();
        boolean result = bookService.createNewBook(book);

        assertTrue(result);
    }

    @Test
    void getById() {
        Book createBook = getCreateBook();
        bookRepository.save(createBook);

        Optional<Book> bookFromRepository = bookRepository.findById(createBook.getId());

        assertTrue(bookFromRepository.isPresent());

        Book actualResult = bookService.getBookById(createBook.getId());
        assertTrue((BooleanSupplier) actualResult);
        assertEquals(createBook.getId(), actualResult.getId());
    }

    private List<Book> getBooks() {
        Book firstBook = new Book();
        Book secondBook = new Book();

        firstBook.setId(1L);
        firstBook.setTitle("ConterStrike");
        firstBook.setAuthor("Glori");
        firstBook.setGenre("Action");
        firstBook.setStatus(Status.ACTIVE);
        firstBook.setPublisher("Ivanov");
        firstBook.setCount(1);

        secondBook.setId(2L);
        secondBook.setTitle("Firary");
        secondBook.setAuthor("Nikram");
        secondBook.setGenre("Auto");
        secondBook.setStatus(Status.ACTIVE);
        secondBook.setPublisher("Petrov");
        secondBook.setCount(1);

        return List.of(firstBook, secondBook);
    }

    private Book getCreateBook() {
        return Book.builder()
                .id(3L)
                .title("WarCraft")
                .author("Ivanov")
                .genre("Fantasy")
                .status(Status.ACTIVE)
                .publisher("Kirilyuk")
                .count(1)
                .build();
    }
}