package com.iase24.springjunit.facade;

import com.iase24.springjunit.component.BookResponse;
import com.iase24.springjunit.component.PaginationInfo;
import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookFacade {

    private final BookService bookService;

    public BookResponse getAllBooks(int page, int size) {
        List<Book> books = bookService.getAll(PageRequest.of(page, size));
        PaginationInfo info = new PaginationInfo();
        info.setAmount(books.size());
        BookResponse response = new BookResponse();
        response.setData(books);
        response.setPaginationInfo(info);
        return response;
    }

    public Book getBookById(Long id) {
        return bookService.getBookById(id);
    }


    public Optional<Book> getBookByIdStatusActive(Long id, Status status) {
        return bookService.getBookByIdStatusActive(id, status);
    }

    public void deleteBookFromCart(Long cartId, Long bookId) {
        bookService.deleteBookFromCart(cartId, bookId);
    }

    public void updateBookCounter(Long id, int count) {
        bookService.updateBookCounter(id, count);
    }

    public List<BookDataDTO> findSearchBook(String text) {
        return bookService.search(text);
    }

    public Node findNodeById(Long nodeId) {
        return bookService.findNodeById(nodeId);
    }

    public BookResponse getBooksByCategoryId(Long categoryId, Boolean parent, int page, int size
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
