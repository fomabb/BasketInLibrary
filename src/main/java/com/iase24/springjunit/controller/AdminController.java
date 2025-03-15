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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Админка", description = "API для управления приложением")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class AdminController {

    private final AdminFacade adminFacade;

    /**
     * Добавление коментария на заданный пользователем вопрос
     *
     * @return JSON answer
     */
    @Operation(summary = "Задать вопрос",
            description = """
                    По ID FAQ написать вопрос.
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
    public FaqAnswerDTO answerForFaq(@RequestBody FaqAnswerDTO answer) {
        return adminFacade.answerForFaq(answer);
    }

    /**
     * Удаление вопросов и ответов
     *
     * @return Response entity ok
     */
    @Operation(summary = "Удаление FAQ",
            description = """
                    По ID FAQ и category ID удалить FAQ.
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
    public ResponseEntity<String> deleteFaq(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("faqId") Long faqId
    ) {
        return adminFacade.deleteFaq(categoryId, faqId);
    }

    /**
     * Посмотреть не прочитанные вопросы
     *
     * @return JSON questions
     */
    @GetMapping("/faq/NotRead")
    public List<Faq> getFaqQuestionNotRead() {
        return adminFacade.getFaqQuestionNotRead();
    }

    /**
     * Добавление админом описания категориипо названию категории
     *
     * @return JSON description
     */
    @Operation(summary = "Добавление описания категории.",
            description = """
                    В теле запроса необходимо указать название категории.
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
    @PostMapping("/newBooks")
    public List<Product> createNewBook(@RequestBody List<Product> product) {
        return adminFacade.createNewBook(product);
    }

    /**
     * Добавление колличества книги на складе
     *
     * @return productUpdateDTO
     */
    @PutMapping("/bookCount")
    public ProductUpdateDTO updateBookCount(@RequestBody ProductUpdateDTO productUpdateDTO) {
        return adminFacade.updateBookCount(productUpdateDTO);
    }

//=======================================================User===========================================================

    /**
     * Выводит всех зарегистрированных пользователей
     *
     * @return JSON all user
     */
    @GetMapping("/allUsers")
    public List<UserDataDTO> getAllUsers() {
        return adminFacade.getAllUsers();
    }

    /**
     * Вывод пользователя с заказами по имени
     *
     * @return user with order
     */
    @GetMapping("/cartByUser")
    public Order getCartByUser(@RequestParam("username") String username) {
        return adminFacade.getCartByUser(username);
    }

    /**
     * Найти пользователя по ID
     *
     * @return user by id
     */
    @GetMapping("/user/{id}")
    public Optional<UserDataDTO> getUserById(@PathVariable("id") Long id) {
        return adminFacade.getUserById(id);
    }

    @PutMapping("/updateRole/{userId}")
    public void updateUserRole(@PathVariable("userId") Long userId) {
        adminFacade.updateUserRole(userId);
    }

//=======================================================Order===========================================================

    /**
     * Показать все зарегистрированные заказы
     *
     * @return all orders
     */
    @GetMapping("/allCarts")
    public List<Order> getCarts() {
        return adminFacade.getCarts();
    }

//=======================================================Tree===========================================================

    /**
     * Создание категории
     *
     * @return JSON
     */
    @PostMapping("/createCategory")
    public List<Node> createNewCategory(@RequestBody List<Node> node) {

        return adminFacade.createNewCategory(node);
    }

    /**
     * Добавление дочерней категории в родительскую
     *
     */
    @Operation(summary = "Добавление дочерней категории в родительскую.",
            description = """
                    В теле запроса необходимо указать ID дочерней категории и родительской.
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
    public void addChildNodeToParent(@RequestBody ChildrenCategoryToParentDataDtoRequest request) {
        adminFacade.addChildNodeToParent(request);
    }

    /**
     * Добавление книг в категорию
     *
     */
    @Operation(summary = "Добавление продукта в категорию.",
            description = """
                    В теле запроса необходимо указать IDs продукта и категории.
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
    public void addBookInCategory(@RequestBody BookToCategoryDataDtoRequest request) {
        adminFacade.addBookInCategory(request);
    }

    /**
     * Добавление продуктов в каатегорию по названию категории
     *
     * @return ResponseEntity
     */
    @PutMapping("/addBooks/inCategory/name")
    public ResponseEntity<?> addBooksInCategoryByName(@RequestParam("categoryName") String categoryName) {
        return adminFacade.addBooksInCategoryByName(categoryName);
    }

//=======================================================ProductOrder=======================================================

    /**
     * Обновление отчета о доставке (1,2,3)
     * 1. Отчет о доставке на пункт пропуска
     * 2. Еслии пользователь забрал продукт
     * 3. Отмена заказа
     *
     * @return ResponseEntity
     */
    @PutMapping("/downloadStatusDelivery/cartId/{cartId}")
    public ResponseEntity<?> deliveryReportController(
            @PathVariable("cartId") Long cartId,
            @RequestBody UpdateDeliveryDTO updateDeliveryDTO
    ) {
        return adminFacade.deliveryReportController(cartId, updateDeliveryDTO);
    }
}
