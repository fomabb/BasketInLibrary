package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Status;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAll(PageRequest pageRequest);

    void createNewBook(List<Book> book);

    Book getBookById(Long id);

    void updateBookCount(Long id, BookUpdateDTO bookUpdateDTO);

    //TODO
    void updateBookCounter(Long id, int count);

    Optional<Book> getBookByIdStatusActive(Long id, Status status);


    void deleteBookFromCart(Long cartId, Long bookId);

    List<BookDataDTO> search(String text);

    void addBookInCategory(Long bookId, Node categoryId);

    void addBooksInCategoryByName(String categoryName);

    void createNewCategory(List<Node> node);

    void addChildrenIdInParentId(Long childrenId, Node parentNode);

    Node findNodeById(Long nodeId);

    List<Book> findBooksChildCategoryId(Long categoryId, boolean parent, PageRequest pageRequest);

    List<DescriptionCategory> findDescriptionCategory(Long categoryId);
}
