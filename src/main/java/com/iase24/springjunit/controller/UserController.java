package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.FaqQuestionDTO;
import com.iase24.springjunit.dto.ProductInCartDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.dto.request.CartProductDataDtoRequest;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.facade.CartFacade;
import com.iase24.springjunit.facade.UserFacade;
import com.iase24.springjunit.service.CartService;
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

    /**
     * Регистрация нового пользователя
     *
     * @return user
     */
    @PostMapping
    public CreateUserDTO createNewUser(@RequestBody CreateUserDTO createUserDTO) {
        return userFacade.createNewUser(createUserDTO);
    }

    /**
     * Найти пользователя по ID
     *
     * @return userDTO
     */
    @GetMapping("/{id}")
    public Optional<UserDataDTO> getUserById(@PathVariable("id") Long id) {
        return userFacade.getUserById(id);
    }

    //TODO
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
    @PostMapping("/faq/question/categoryId/{categoryId}")
    public FaqQuestionDTO questionCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody FaqQuestionDTO question
    ) {
        return userFacade.questionCategory(categoryId, question);
    }

    /**
     * Обновление текста вопроса
     *
     * @return question
     */
    @PutMapping("/faq/update/faqId/{faqId}")
    public FaqQuestionDTO updateQuestion(
            @PathVariable("faqId") Long faqId,
            @RequestBody FaqQuestionDTO question
    ) {
        return userFacade.updateQuestion(faqId, question);
    }

    /**
     * Удаление из категории комментария
     */
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
    @GetMapping("/cartId/{id}")
    public Cart getCartById(@PathVariable("id") Long id) {
        return cartFacade.getCartById(id);
    }

    /**
     * Вывести все товары из корзины
     *
     * @return products
     */
    @GetMapping("/cart/allBooksInCart/cartId/{cartId}")
    public List<ProductInCartDataDTO> getProductsInCartById(@PathVariable("cartId") Long cartId) {
        return cartService.findProductInCartById(cartId);
    }

    /**
     * Добавление товара в корзину
     *
     * @return cart with things
     */
    @PostMapping("/addBookInCart")
    public Cart addProductInCart(@RequestBody CartProductDataDtoRequest reques) {
        return cartService.addProductInCart(reques);
    }

    /**
     * Добавление/уменьшение заказов в корзине
     *
     * @return quantity
     */
    @PutMapping("/updateBookQuantityInCart/cartId/{basketId}/bookId/{bookId}")
    public UpdateBookQuantityInBasket updateQuantity(
            @PathVariable("basketId") Long cartId,
            @PathVariable("bookId") Long productId,
            @RequestBody UpdateBookQuantityInBasket updateBookQuantity
    ) {
        return cartService.updateQuantityInCart(cartId, productId, updateBookQuantity);
    }

    /**
     * Удаление товара из корзины
     */
    @DeleteMapping("/removeBookInCart/cartId/{cartId}/bookId/{bookId}")
    public void removeBookInBasket(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long productId
    ) {
        cartService.removeProductInCart(cartId, productId);
    }
}
