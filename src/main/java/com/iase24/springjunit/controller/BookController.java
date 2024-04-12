package com.iase24.springjunit.controller;

import com.iase24.springjunit.component.BookResponse;
import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.facade.BookFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/book")
@RequiredArgsConstructor
@Transactional
public class BookController {

    private final BookFacade bookFacade;

    /**
     * Найти все книги
     *
     * @return List<Books>
     */
    @GetMapping
    public BookResponse getAllBooks(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return bookFacade.getAllBooks(page, size);
    }

    /**
     * Найти книгу по ID
     *
     * @return book
     */
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") Long id) {
        return bookFacade.getBookById(id);
    }

    @GetMapping("/active/{id}")
    public Optional<Book> getBookByIdStatusActive(
            @PathVariable("id") Long id,
            @RequestParam("status") Status status
    ) {
        return bookFacade.getBookByIdStatusActive(id, status);
    }

    /**
     *
     */
    @DeleteMapping("/user/cartId/{cartId}/bookId/{bookId}")
    public ResponseEntity<?> deleteBookFromCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
    ) {
        return bookFacade.deleteBookFromCart(cartId, bookId);
    }

    @PutMapping("/update/counter")
    public void updateBookCounter(
            @RequestParam Long id, @RequestParam int count
    ) {
        bookFacade.updateBookCounter(id, count);
    }

    /**
     * Полнотекстовый поиск всех товаров
     *
     * @return books by text
     */
    @GetMapping("/search")
    public List<BookDataDTO> findSearchBook(@RequestParam String text) {
        return bookFacade.findSearchBook(text);
    }

    /**
     * Показать иерархию категорий
     *
     * @return category by ID
     */
    @GetMapping("/node/{nodeId}")
    public Node findNodeById(@PathVariable("nodeId") Long nodeId) {
        return bookFacade.findNodeById(nodeId);
    }

    /**
     * Показать книги в категории
     *
     * @return books
     */
    @GetMapping("/category/{categoryId}")
    public BookResponse getBooksByCategoryId(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam Boolean parent,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return bookFacade.getBooksByCategoryId(categoryId, parent, page, size);
    }
}
