package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.DescriptionDataDTO;
import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.dto.UpdateDeliveryDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.dto.request.BookToCategoryDataDtoRequest;
import com.iase24.springjunit.dto.request.ChildrenCategoryToParentDataDtoRequest;
import com.iase24.springjunit.dto.response.CommonExceptionResponse;
import com.iase24.springjunit.entities.Faq;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.facade.AdminFacade;
import com.iase24.springjunit.service.AdminService;
import com.iase24.springjunit.service.OrderService;
import com.iase24.springjunit.service.ProductService;
import com.iase24.springjunit.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Админка", description = "API для управления приложением")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class AdminController {

    private final AdminFacade adminFacade;
    private final AdminService adminService;
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    /**
     * Добавление комментария на заданный пользователем вопрос
     *
     * @return JSON answer
     */
    @Operation(summary = "Задать вопрос",
            description = """
                    ```По ID FAQ написать вопрос.```
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "ОК",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = FaqAnswerDTO.class)))
                            }),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            })
    @PutMapping("/answer/faqId")
    public ResponseEntity<FaqAnswerDTO> answerForFaq(@RequestBody FaqAnswerDTO answer) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminFacade.answerForFaq(answer));
    }

    /**
     * Удаление вопросов и ответов
     *
     * @return Response entity ok
     */
    @Operation(summary = "Удаление FAQ",
            description = """
                    ```По ID FAQ и category ID удалить FAQ.```
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "ОК",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = String.class)))
                            }),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            })
    @DeleteMapping("/categoryId/{categoryId}/faqId/{faqId}")
    public ResponseEntity<?> deleteFaq(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("faqId") Long faqId
    ) {
        adminService.deleteFaq(categoryId, faqId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Посмотреть не прочитанные вопросы
     *
     * @return JSON questions
     */
    @GetMapping("/faq/NotRead")
    public ResponseEntity<List<Faq>> getFaqQuestionNotRead() {
        return ResponseEntity.ok(adminFacade.getFaqQuestionNotRead());
    }

    /**
     * Добавление админом описания категории по названию категории
     *
     * @return JSON description
     */
    @Operation(summary = "Добавление описания категории.",
            description = """
                    ```В теле запроса необходимо указать название категории.```
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "ОК",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = DescriptionDataDTO.class)))
                            }),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            })
    @PostMapping("/create/descriptionByName")
    public ResponseEntity<String> createDescriptionByCategoryName(@RequestBody DescriptionDataDTO descriptionCategory) {
        return adminFacade.createDescriptionByCategoryName(descriptionCategory);
    }

//=======================================================Product===========================================================

    /**
     * Добавление новых книг на склад
     *
     * @return JSON created products
     */
    @Operation(
            summary = "Добавление новых книг на склад.",
            description = """
                    ```В теле запроса необходимо в список добавить книгу(и).```
                    """
    )
    @PostMapping("/newBooks")
    public ResponseEntity<List<Product>> createNewBook(@RequestBody List<Product> product) {
        productService.createNewBook(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    /**
     * Добавление количества книги на складе
     *
     * @return productUpdateDTO
     */
    @Operation(
            summary = "Добавление(уменьшение) количества книги на складе.",
            description = """
                    ```В теле запроса необходимо прописать обновить count книг.```
                    """
    )
    @PutMapping("/bookCount")
    public ResponseEntity<ProductUpdateDTO> updateBookCount(@RequestBody ProductUpdateDTO productUpdateDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(productService.updateBookCount(productUpdateDTO));
    }

//=======================================================User===========================================================

    /**
     * Выводит всех зарегистрированных пользователей
     *
     * @return JSON all user
     */
    @Operation(
            summary = "Выводит всех зарегистрированных пользователей.",
            description = """
                    ```Показывает всех зарегистрированных пользователей.```
                    """
    )
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDataDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Вывод пользователя с заказами по имени
     *
     * @return user with order
     */
    @Operation(
            summary = "Вывод пользователя с заказами по имени.",
            description = """
                    ```В параметре необходимо указать имя пользователя.```
                    """
    )
    @GetMapping("/cartByUser")
    public ResponseEntity<Order> getCartByUser(@RequestParam("username") String username) {
        return ResponseEntity.ok(orderService.getOrderByLogin(username));
    }

    /**
     * Найти пользователя по ID
     *
     * @return user by id
     */
    @Operation(
            summary = "Найти пользователя по ID.",
            description = """
                    ```В путь необходимо ввести ID пользователя.```
                    """
    )
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDataDTO> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Hidden
    @PutMapping("/updateRole/{userId}")
    public void updateUserRole(@PathVariable("userId") Long userId) {
        adminService.updateUserRolesByUsername(userId);
    }

//=======================================================Order===========================================================

    /**
     * Показать все зарегистрированные заказы
     *
     * @return all orders
     */
    @Operation(
            summary = "Показать все зарегистрированные заказы.",
            description = """
                    ```Показать все зарегистрированные заказы.```
                    """
    )
    @GetMapping("/allCarts")
    public ResponseEntity<List<Order>> getCarts() {
        return ResponseEntity.ok(orderService.getOrders());
    }

//=======================================================Tree===========================================================

    /**
     * Создание категории
     *
     * @return JSON
     */
    @Operation(
            summary = "Создание списка категорий.",
            description = """
                    ```В теле запроса необходимо внести в список категорию(и).```
                    """
    )
    @PostMapping("/createCategory")
    public ResponseEntity<List<Node>> createNewCategory(@RequestBody List<Node> node) {
        productService.createNewCategory(node);
        return ResponseEntity.status(HttpStatus.CREATED).body(node);
    }

    /**
     * Добавление дочерней категории в родительскую
     */
    @Operation(summary = "Добавление дочерней категории в родительскую.",
            description = """
                    ```В теле запроса необходимо указать ID дочерней категории и родительской.```
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "ОК",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ChildrenCategoryToParentDataDtoRequest.class)))
                            }),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            })
    @PutMapping("/add-child-node/to/parent-node")
    public ResponseEntity<Void> addChildNodeToParent(@RequestBody ChildrenCategoryToParentDataDtoRequest request) {
        productService.addChildNodeToParent(request);
        return ResponseEntity.accepted().build();
    }

    /**
     * Добавление книг в категорию
     */
    @Operation(summary = "Добавление продукта в категорию.",
            description = """
                    ```В теле запроса необходимо указать IDs продукта и категории.```
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "ОК",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookToCategoryDataDtoRequest.class)))
                            }),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            })
    @PutMapping("/add-book-to-category")
    public ResponseEntity<Void> addBookInCategory(@RequestBody BookToCategoryDataDtoRequest request) {
        productService.addBookInCategory(request);
        return ResponseEntity.accepted().build();
    }

    /**
     * Добавление продуктов в категорию по названию категории
     *
     * @return ResponseEntity
     */
    @Operation(
            summary = "Добавление продуктов в категорию по имени категории.",
            description = """
                    ```Необходимо в теле запроса написать имя категории.```
                    """
    )
    @PutMapping("/addBooks/inCategory/name")
    public ResponseEntity<?> addBooksInCategoryByName(@RequestParam("categoryName") String categoryName) {
        return adminFacade.addBooksInCategoryByName(categoryName);
    }

//=======================================================ProductOrder=======================================================

    /**
     * Обновление отчета о доставке (1,2,3)
     * 1. Отчет о доставке на пункт пропуска
     * 2. Если пользователь забрал продукт
     * 3. Отмена заказа
     *
     * @return ResponseEntity
     */
    @Operation(
            summary = "Обновление отчета о доставке.",
            description = """
                    ```
                    Обновление отчета о доставке (1,2,3)
                            * 1. Отчет о доставке на пункт пропуска
                            * 2. Если пользователь забрал продукт
                            * 3. Отмена заказа
                    ```
                    """
    )
    @PutMapping("/downloadStatusDelivery/cartId/{cartId}")
    public ResponseEntity<Void> deliveryReportController(
            @PathVariable("cartId") Long cartId,
            @RequestBody UpdateDeliveryDTO updateDeliveryDTO
    ) {
        adminFacade.deliveryReportController(cartId, updateDeliveryDTO);
        return ResponseEntity.accepted().build();
    }
}
