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
@RequestMapping(value = "/api")
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

    @PostMapping
    public List<Book> createNewBook(@RequestBody List<Book> book) {
        bookService.createNewBook(book);
        return book;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public BookUpdateDTO updateBookCount(
            @PathVariable("id") Long id,
            @RequestBody BookUpdateDTO bookUpdateDTO
    ) {

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

    @PutMapping("update/counter")
    public void updateBookCounter(@RequestParam Long id, @RequestParam int count) {

        bookService.updateBookCounter(id, count);
    }

    @GetMapping("/search")
    public List<BookDataDTO> findSearchBook(@RequestParam String text) {

        return bookService.search(text);
    }

//=======================================================Tree===========================================================

    @PostMapping("/createCategory")
    public List<Node> createNewCategory(@RequestBody List<Node> node) {

        bookService.createNewCategory(node);

        return node;
    }

    @PutMapping("/addChildrenId/{childrenId}")
    public Node addChildrenIdInParentId(
            @PathVariable("childrenId") Long childrenId,
            @RequestParam Node parentNode
    ) {

        bookService.addChildrenIdInParentId(childrenId, parentNode);

        return parentNode;
    }

    @PutMapping("addBookId/{bookId}/categoryId/{categoryId}")
    public Node addBookInCategory(
            @PathVariable("bookId") Long bookId,
            @PathVariable("categoryId") Node categoryId
    ) {

        bookService.addBookInCategory(bookId, categoryId);

        return categoryId;
    }

    @GetMapping("/node/{nodeId}")
    public Node findNodeById(@PathVariable("nodeId") Long nodeId) {

        return bookService.findNodeById(nodeId);
    }

    @GetMapping("/book/category")
    public BookResponse getBooksByCategoryId(
//            @PathVariable("categoryId") Long categoryId,
            @RequestParam Long categoryId,
//            @RequestParam String category,
            @RequestParam Boolean parent,
            @RequestParam int page,
            @RequestParam int size
    ) {

        List<DescriptionCategory> description = bookService.findDescriptionCategory(categoryId);
        List<Book> books = bookService.findBooksChildCategoryId(categoryId, parent, PageRequest.of(page, size));

        PaginationInfo info = new PaginationInfo();
        info.setAmount(books.size());

        BookResponse response = new BookResponse();
        response.setData(books);
        response.setPaginationInfo(info);
        response.setDescriptionData(description);

        return response;
    }
}
