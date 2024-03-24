package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookAddCartDTO;
import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> getBasket(Long id) {

        return bookRepository.getBasketById(id);
    }

    @Override
    public Optional<Book> getBookByIdStatusActive(Long id, Status status) {

        if (status == Status.ACTIVE) {
            return bookRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> getBookByTitle(String title) {

        return bookRepository.findBookByTitle(title);
    }

    @Override
    public List<Book> getAllByGenre(String genre) {
//        return bookRepository.findAll().stream()
//                .filter(book -> genre.equals(book.getGenre().toLowerCase(Locale.ROOT)))
//                .toList();

        return bookRepository.findAllByGenre(genre);
    }

    @Override
    public List<Book> getBookByAuthor(String author) {

        return bookRepository.findAllByAuthor(author);
    }

    @Override
    public List<Book> getAll() {

        return bookRepository.findAll();
    }

    @Override
    public boolean createNewBook(List<Book> book) {
        if (book != null) {
            bookRepository.saveAllAndFlush(book);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void updateBookCount(Long id, BookUpdateDTO bookUpdateDTO) throws IllegalAccessException {
        Book book = getBookById(id).orElse(null);

        assert book != null;
        book.setCount(bookUpdateDTO.getCount());

        if (book.getCount() > 0) {
            book.setStatus(Status.ACTIVE);
        } else if (book.getCount() == 0) {
            book.setStatus(Status.INACTIVE);
        } else {
            throw new IllegalAccessException("IllegalAccessException");
        }

        Book updateBook = bookRepository.save(book);
        new BookUpdateDTO(updateBook.getCount(), updateBook.getStatus());
    }

    @Override
    public void updateBookCounter(Long id, int count) {

        Book book = getBookById(id).orElse(null);

        if (book != null) {
            if (count <= 0) {
                book.setStatus(Status.INACTIVE);
            } else {
                book.setStatus(Status.ACTIVE);
            }

            bookRepository.saveAndFlush(book);
        }
    }
}
