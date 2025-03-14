package com.iase24.springjunit.controller;

import com.iase24.springjunit.component.BookResponse;
import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.facade.ProductFacade;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Склад продуктов", description = "API для управления продуктами")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class ProductController {

    private final ProductFacade productFacade;

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
        return productFacade.getAllBooks(page, size);
    }

    /**
     * Найти книгу по ID
     *
     * @return product
     */
    @GetMapping("/{id}")
    public Product getBookById(@PathVariable("id") Long id) {
        return productFacade.getBookById(id);
    }

    @GetMapping("/active/{id}")
    public Optional<Product> getBookByIdStatusActive(
            @PathVariable("id") Long id,
            @RequestParam("status") Status status
    ) {
        return productFacade.getBookByIdStatusActive(id, status);
    }

    /**
     *
     */
    @DeleteMapping("/user/cartId/{cartId}/bookId/{bookId}")
    public ResponseEntity<?> deleteBookFromCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
    ) {
        return productFacade.deleteBookFromCart(cartId, bookId);
    }

    @PutMapping("/update/counter")
    public void updateBookCounter(
            @RequestParam Long id, @RequestParam int count
    ) {
        productFacade.updateBookCounter(id, count);
    }

    /**
     * Полнотекстовый поиск всех товаров
     *
     * @return products by text
     */
    @GetMapping("/search")
    public List<BookDataDTO> findSearchBook(@RequestParam String text) {
        return productFacade.findSearchBook(text);
    }

    /**
     * Показать иерархию категорий
     *
     * @return category by ID
     */
    @GetMapping("/node/{nodeId}")
    public Node findNodeById(@PathVariable("nodeId") Long nodeId) {
        return productFacade.findNodeById(nodeId);
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
        return productFacade.getBooksByCategoryId(categoryId, parent, page, size);
    }
}
