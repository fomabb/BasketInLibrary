package com.iase24.springjunit.facade;

import com.iase24.springjunit.component.BookResponse;
import com.iase24.springjunit.component.PaginationInfo;
import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<BookDataDTO> findSearchBook(String text) {
        return productService.search(text);
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
