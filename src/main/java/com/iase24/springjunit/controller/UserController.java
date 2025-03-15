package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.FaqQuestionDTO;
import com.iase24.springjunit.dto.ProductInCartDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasketRequest;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.dto.request.CartProductDataDtoRequest;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.facade.CartFacade;
import com.iase24.springjunit.facade.UserFacade;
import com.iase24.springjunit.service.CartService;
import com.iase24.springjunit.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи с корзиной", description = "API для управления пользователями и их корзиной")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class UserController {

    private final UserFacade userFacade;
    private final CartFacade cartFacade;
    private final CartService cartService;
    private final UserService userService;

    /**
     * Регистрация нового пользователя
     *
     * @return user
     */
    @Operation(
            summary = "Регистрация нового пользователя.",
            description = """
                    ```В теле запроса ввести необходимые данные.```
                    """
    )
    @PostMapping
    public ResponseEntity<UserDataDTO> createNewUser(@RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(createUserDTO));
    }

    /**
     * Найти пользователя по ID
     *
     * @return userDTO
     */
    @Operation(
            summary = "Найти пользователя по ID.",
            description = """
                    ```В путь добавить ID пользователя.```
                    """
    )
    @GetMapping("/{id}")
    public UserDataDTO getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    //TODO
    @Hidden
    @GetMapping("/cart/userId/{userId}")
    public Optional<UserDataDTO> getCartByUserId(
            @PathVariable("userId") Long userId
    ) {
        return userFacade.getCartByUserId(userId);
    }

//===========================================FAQ========================================================================

    /**
     * Задать вопрос по категории
     *
     * @return question {DateTime/question}
     */
    @Operation(
            summary = "Написать вопрос внутри категории.",
            description = """
                    ```Добавить в путь ID категории, в теле запроса написать вопрос.```
                    """
    )
    @PostMapping("/faq/question/categoryId/{categoryId}")
    public ResponseEntity<Void> questionCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody FaqQuestionDTO question
    ) {
        userService.questionCategory(categoryId, question);
        return ResponseEntity.accepted().build();
    }

    /**
     * Обновление текста вопроса
     *
     * @return question
     */
    @Operation(
            summary = "Обновление текста вопроса.",
            description = """
                    ```Добавить в путь ID FAQ, в теле запроса обновить текст.```
                    """
    )
    @PutMapping("/faq/update/faqId/{faqId}")
    public ResponseEntity<Void> updateQuestion(
            @PathVariable("faqId") Long faqId,
            @RequestBody FaqQuestionDTO question
    ) {
        userService.updateQuestion(faqId, question);
        return ResponseEntity.accepted().build();
    }

    /**
     * Удаление из категории комментария
     */
    @Operation(
            summary = "Удаление комментария в категории.",
            description = """
                    ```Добавить в путь IDs категории и FAQ.```
                    """
    )
    @DeleteMapping("/faq/categoryId/{categoryId}/faqId/{faqId}")
    public ResponseEntity<String> removeFaqFromCategory(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("faqId") Long faqId
    ) {
        return userFacade.removeFaqFromCategory(categoryId, faqId);
    }

//===========================================Cart=====================================================================

    /**
     * Найти корзину по ID пользователя
     *
     * @return cart with things
     */
    @Operation(
            summary = "Найти корзину по ID пользователя.",
            description = """
                    ```В путь добавит ID корзины.```
                    """
    )
    @GetMapping("/cartId/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cartFacade.getCartById(id));
    }

    /**
     * Вывести все товары из корзины
     *
     * @return products
     */
    @Operation(
            summary = "Вывести все товары из корзины по ее ID.",
            description = """
                    ```В путь добавить ID категории.```
                    """
    )
    @GetMapping("/cart/allBooksInCart/cartId/{cartId}")
    public List<ProductInCartDataDTO> getProductsInCartById(@PathVariable("cartId") Long cartId) {
        return cartService.findProductInCartById(cartId);
    }

    /**
     * Добавление товара в корзину
     *
     * @return cart with things
     */
    @Operation(
            summary = "Добавление продукта в корзину.",
            description = """
                    ```В теле запроса добавить IDs продукта и корзины.```
                    """
    )
    @PostMapping("/addBookInCart")
    public ResponseEntity<Cart> addProductInCart(@RequestBody CartProductDataDtoRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(cartService.addProductInCart(request));
    }

    /**
     * Добавление/уменьшение заказов в корзине
     *
     * @return quantity
     */
    @Operation(
            summary = "Добавление/уменьшение заказов в корзине.",
            description = """
                    ```В теле запроса указать IDs корзины, продукта и в написать количество.```
                    """
    )
    @PutMapping("/updateBookQuantityInCart")
    public UpdateBookQuantityInBasketRequest updateQuantity(
            @RequestBody UpdateBookQuantityInBasketRequest request
    ) {
        return cartService.updateQuantityInCart(request);
    }

    /**
     * Удаление товара из корзины
     */
    @Operation(
            summary = "Удаление товара из корзины.",
            description = """
                    ```В пути добавить IDs корзины и продукта.```
                    """
    )
    @DeleteMapping("/removeBookInCart/cartId/{cartId}/bookId/{bookId}")
    public void removeBookInBasket(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long productId
    ) {
        cartService.removeProductInCart(cartId, productId);
    }
}
