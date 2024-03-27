package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CartRepository cartRepository;

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

        return bookRepository.findAllByGenre(genre);
    }

    @Override
    public List<Book> getBookByAuthor(String author) {

        return bookRepository.findAllByAuthor(author);
    }

    @Override
    public void deleteBookFromCart(Long cartId, Long bookId) {
        cartRepository.findById(cartId);
        bookRepository.deleteById(bookId);
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
    public Book getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            throw new IllegalArgumentException("Book with id " + id + " not found");
        }
    }

    @Override
    public void updateBookCount(Long id, BookUpdateDTO bookUpdateDTO) throws IllegalAccessException {
        Book book = getBookById(id);

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

    //TODO

    @Override
    public void updateBookCounter(Long id, int count) {

        Book book = getBookById(id);

        if (book != null) {
            if (count <= 0) {
                book.setStatus(Status.INACTIVE);
            } else {
                book.setStatus(Status.ACTIVE);
            }
            bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<Book> search(String text) {

        return bookRepository.search(text);
    }
}
