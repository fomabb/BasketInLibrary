package com.iase24.springjunit.controller;

import com.iase24.springjunit.component.BookResponse;
import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.facade.BookFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @return product
     */
    @GetMapping("/{id}")
    public Product getBookById(@PathVariable("id") Long id) {
        return bookFacade.getBookById(id);
    }

    @GetMapping("/active/{id}")
    public Optional<Product> getBookByIdStatusActive(
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
     * @return products by text
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
     * @return products
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
