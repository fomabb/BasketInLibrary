package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Status;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllByGenre(String genre);

    List<Book> getAll();

    boolean createNewBook(Book book);

    Optional<Book> getBookById(Long id);

    void updateBookCount(Long id, BookUpdateDTO bookUpdateDTO) throws IllegalAccessException;

    void updateBookCounter(Long id, int count);

    List<Book> getAllInactive(String inActive);

    List<Book> getBasket(Long id);

    Optional<Book> getBookByIdStatusActive(Long id, Status status);
}
