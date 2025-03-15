package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.dto.request.BookToCategoryDataDtoRequest;
import com.iase24.springjunit.dto.request.ChildrenCategoryToParentDataDtoRequest;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Product;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {

    List<Product> getAll(PageRequest pageRequest);

    void createNewBook(List<Product> product);

    Product getBookById(Long id);

    ProductUpdateDTO updateBookCount(ProductUpdateDTO dto);

    //TODO
    void updateBookCounter(Long id, int count);

    Product getBookByIdStatusActive(Long id);

    void deleteBookFromCart(Long cartId, Long bookId);

    List<BookDataDTO> search(String text);

    void addBookInCategory(BookToCategoryDataDtoRequest dataDtoRequest);

    void addBooksInCategoryByName(String categoryName);

    void createNewCategory(List<Node> node);

    void addChildNodeToParent(ChildrenCategoryToParentDataDtoRequest dataDtoRequest);

    Node findNodeById(Long nodeId);

    List<Product> findBooksChildCategoryId(Long categoryId, boolean parent, PageRequest pageRequest);

    List<DescriptionCategory> findDescriptionCategory(Long categoryId);
}
