package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
    ) {

        if (genre != null) {
            return bookService.getAllByGenre(genre);
        }
        if (title != null) {
            return bookService.getBookByTitle(title);
        }
        if (author != null) {
            return bookService.getBookByAuthor(author);
        }
        return bookService.getAll();
    }

    @PostMapping
    public List<Book> createNewBook(@RequestBody List<Book> book) {
        bookService.createNewBook(book);
        return book;
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public BookUpdateDTO updateBookCount(
            @PathVariable("id") Long id,
            @RequestBody BookUpdateDTO bookUpdateDTO
    ) throws IllegalAccessException {

        if (id != null) {
            bookService.updateBookCount(id, bookUpdateDTO);
        }

        return bookUpdateDTO;
    }

    @GetMapping("/active/{id}")
    public Optional<Book> getBookByIdStatusActive(@PathVariable("id") Long id,
                                                  @RequestParam("status") Status status) {

        return bookService.getBookByIdStatusActive(id, status);
    }

    @DeleteMapping("cartId/{cartId}/bookId/{bookId}")
    public void deleteBookFromCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
    ) {

        bookService.deleteBookFromCart(cartId, bookId);
    }
}
