package com.iase24.springjunit.controller;

import com.iase24.springjunit.component.BookResponse;
import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.dto.request.ProductQuantityDataDtoRequest;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.facade.ProductFacade;
import com.iase24.springjunit.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Склад продуктов", description = "API для управления продуктами")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class ProductController {

    private final ProductFacade productFacade;
    private final ProductService productService;

    /**
     * Найти все книги
     *
     * @return List<Books>
     */
    @Operation(
            summary = "Вывести все книги.",
            description = """
                    ```Вывести все книги.```
                    """
    )
    @GetMapping
    public ResponseEntity<BookResponse> getAllBooks(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(productFacade.getAllBooks(page - 1, size));
    }

    /**
     * Найти книгу по ID
     *
     * @return product
     */
    @Operation(
            summary = "Найти книгу по ID",
            description = """
                    ```Необходимо в путь добавить ID продукта.```
                    """
    )
    @GetMapping("/{id}")
    public ResponseEntity<Product> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getBookById(id));
    }

    @Operation(
            summary = "Показать книгу по ID если статус активный.",
            description = """
                    ```Необходимо в путь добавить ID продукта, если книга не активная то будет сообщение, не доступно.```
                    """
    )
    @GetMapping("/active/{id}")
    public Product getBookByIdStatusActive(@PathVariable("id") Long id) {
        return productService.getBookByIdStatusActive(id);
    }

    /**
     *
     */
    @Operation(
            summary = "Удалить книгу из корзины заказов.",
            description = """
                    ```В путь прописать IDs корзины и книги```
                    """
    )
    @DeleteMapping("/user/cartId/{cartId}/bookId/{bookId}")
    public ResponseEntity<?> deleteBookFromCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
    ) {
        productService.deleteBookFromCart(cartId, bookId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Обновление количества продукта.",
            description = """
                    ```В теле запроса необходимо прописать ID продукта и количество в зависимости от желаемого.```
                    """
    )
    @PutMapping("/update-quantity-product")
    public void updateBookCounter(@RequestBody ProductQuantityDataDtoRequest request) {
        productService.updateBookCounter(request);
    }

    /**
     * Полнотекстовый поиск всех товаров
     *
     * @return products by text
     */
    @Operation(
            summary = "Полнотекстовый поиск.",
            description = """
                    ```В параметр необходимо добавить текст, по которому нужно осуществить поиск.```
                    """
    )
    @GetMapping("/search")
    public ResponseEntity<List<BookDataDTO>> findSearchBook(@RequestParam String text) {
        return ResponseEntity.ok(productFacade.findSearchBook(text));
    }

    /**
     * Показать иерархию категорий
     *
     * @return category by ID
     */
    @Operation(
            summary = "Показать иерархию категорий.",
            description = """
                    [ПЕРЕРАБОТАТЬ НЕОБХОДИМО]
                    """
    )
    @GetMapping("/node/{nodeId}")
    public ResponseEntity<Node> findNodeById(@PathVariable("nodeId") Long nodeId) {
        return ResponseEntity.ok(productService.findNodeById(nodeId));
    }

    /**
     * Показать книги в категории
     *
     * @return products
     */
    @Operation(
            summary = "Показать книги в категории.",
            description = """
                    ```
                    Необходимо добавить в путь ID категории, в параметр [переработается] parent: указать, дочерняя
                    или родительская категория. Также необходимо указать какая страница и размер предметов на странице.
                    ```
                    """
    )
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<BookResponse> getBooksByCategoryId(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam Boolean parent,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(productFacade.getBooksByCategoryId(categoryId, parent, page - 1, size));
    }
}
