package com.iase24.springjunit.controller;

import com.iase24.springjunit.component.BookResponse;
import com.iase24.springjunit.component.PaginationInfo;
import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    private final BookService bookService;

    @GetMapping
    public BookResponse getAllBooks(
            @RequestParam int page,
            @RequestParam int size
    ) {
        List<Book> books = bookService.getAll(PageRequest.of(page, size));

        PaginationInfo info = new PaginationInfo();
        info.setAmount(books.size());

        BookResponse response = new BookResponse();
        response.setData(books);
        response.setPaginationInfo(info);

        return response;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") Long id) {

        return bookService.getBookById(id);
    }


    @GetMapping("/active/{id}")
    public Optional<Book> getBookByIdStatusActive(
            @PathVariable("id") Long id,
            @RequestParam("status") Status status
    ) {

        return bookService.getBookByIdStatusActive(id, status);
    }

    @DeleteMapping("/user/cartId/{cartId}/bookId/{bookId}")
    public void deleteBookFromCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
    ) {
        bookService.deleteBookFromCart(cartId, bookId);
    }

    @PutMapping("/update/counter")
    public void updateBookCounter(
            @RequestParam Long id, @RequestParam int count
    ) {
        bookService.updateBookCounter(id, count);
    }

    @GetMapping("/search")
    public List<BookDataDTO> findSearchBook(@RequestParam String text) {

        return bookService.search(text);
    }

    @GetMapping("/node/{nodeId}")
    public Node findNodeById(@PathVariable("nodeId") Long nodeId) {

        return bookService.findNodeById(nodeId);
    }

    @GetMapping("/category/{categoryId}")
    public BookResponse getBooksByCategoryId(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam Boolean parent,
            @RequestParam int page,
            @RequestParam int size
    ) {
        List<Book> books = bookService.findBooksChildCategoryId(categoryId, parent, PageRequest.of(page, size));
        List<DescriptionCategory> description = bookService.findDescriptionCategory(categoryId);

        PaginationInfo info = new PaginationInfo();
        info.setAmount(books.size());

        BookResponse response = new BookResponse();
        response.setData(books);
        response.setPaginationInfo(info);
        response.setDescriptionData(description);

        return response;
    }
}
