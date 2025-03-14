package com.iase24.springjunit.facade;

import com.iase24.springjunit.component.BookResponse;
import com.iase24.springjunit.component.PaginationInfo;
import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;

    public BookResponse getAllBooks(int page, int size) {
        List<Product> products = productService.getAll(PageRequest.of(page, size));
        PaginationInfo info = new PaginationInfo();
        info.setAmount(products.size());
        BookResponse response = new BookResponse();
        response.setData(products);
        response.setPaginationInfo(info);
        return response;
    }

    public Product getBookById(Long id) {
        return productService.getBookById(id);
    }


    public Product getBookByIdStatusActive(Long id, Status status) {
        return productService.getBookByIdStatusActive(id, status);
    }

    public ResponseEntity<?> deleteBookFromCart(Long cartId, Long bookId) {
        productService.deleteBookFromCart(cartId, bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void updateBookCounter(Long id, int count) {
        productService.updateBookCounter(id, count);
    }

    public List<BookDataDTO> findSearchBook(String text) {
        return productService.search(text);
    }

    public Node findNodeById(Long nodeId) {
        return productService.findNodeById(nodeId);
    }

    public BookResponse getBooksByCategoryId(Long categoryId, Boolean parent, int page, int size
    ) {
        List<Product> products = productService.findBooksChildCategoryId(categoryId, parent, PageRequest.of(page, size));
        List<DescriptionCategory> description = productService.findDescriptionCategory(categoryId);
        PaginationInfo info = new PaginationInfo();
        info.setAmount(products.size());
        BookResponse response = new BookResponse();
        response.setData(products);
        response.setPaginationInfo(info);
        response.setDescriptionData(description);
        return response;
    }
}
