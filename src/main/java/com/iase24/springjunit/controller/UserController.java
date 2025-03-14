package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.ProductInCartDataDTO;
import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.FaqQuestionDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.ProductOrder;
import com.iase24.springjunit.facade.CartFacade;
import com.iase24.springjunit.facade.UserFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@Valid
@Tag(name = "Пользователи", description = "API для управления пользователями")
public class UserController {

    private final UserFacade userFacade;
    private final CartFacade cartFacade;

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

//===========================================Order=======================================================================

    /**
     * Оформление заказа по ID товара
     */
    @PutMapping("/cartId/{cartId}/bookId/{bookId}")
    public Order addBookInCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
    ) {
        return userFacade.addBookInCart(cartId, bookId);
    }

    /**
     * Найти заказ по ID
     *
     * @return order
     */
    @GetMapping("/cartId/{cartId}")
    public Order getCartById(@PathVariable("cartId") Long cartId) {

        return userFacade.getCartById(cartId);
    }

    /**
     * Отменить сформированный заказ
     */
    @DeleteMapping("cartId/{cartId}/bookId/{bookId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long cartId,
            @PathVariable Long bookId
    ) {
        return userFacade.removeFromCart(cartId, bookId);
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
     * Обновление тексте вопроса
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
     * Удаление из категории коментария
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
    @GetMapping("/basketId/{id}")
    public Cart getBasketById(@PathVariable("id") Long id) {
        return cartFacade.getCartById(id);
    }

    /**
     * Вывести все товары из корзины
     *
     * @return products
     */
    @GetMapping("/basket/allBooksInBasket/basketId/{basketId}")
    public List<ProductInCartDataDTO> getBooksInBasketById(@PathVariable("basketId") Long basketId) {
        return cartFacade.getProductsInCartById(basketId);
    }

    /**
     * Добавление товара в корзину
     *
     * @return cart with things
     */
    @PostMapping("/addBookInBasket/basketId/{basketId}/bookId/{bookId}")
    public Cart createBasket(@PathVariable("basketId") Long basketId, @PathVariable("bookId") Long bookId) {
        return cartFacade.createCart(basketId, bookId);
    }

    /**
     * Добавление/уменьшение заказов в корзине
     *
     * @return quantity
     */
    @PutMapping("/updateBookQuantityInBasket/basketId/{basketId}/bookId/{bookId}")
    public UpdateBookQuantityInBasket updateQuantity(
            @PathVariable("basketId") Long basketId,
            @PathVariable("bookId") Long bookId,
            @RequestBody UpdateBookQuantityInBasket updateBookQuantity
    ) {
        return cartFacade.updateQuantity(basketId, bookId, updateBookQuantity);
    }

    /**
     * Удаление товара из корзины
     */
    @DeleteMapping("/removeBookInBasket/basketId/{basketId}/bookId/{bookId}")
    public ResponseEntity<?> removeBookInBasket(
            @PathVariable("basketId") Long basketId,
            @PathVariable("bookId") Long bookId
    ) {
        return cartFacade.removeProductInCart(basketId, bookId);
    }

    /**
     * Сформировать заказ по колличеству товара
     *
     * @return order
     */
    @PostMapping("/createOrdersByQuantityInBasket/basketId/{basketId}/bookId/{bookId}")
    public ResponseEntity<Product> toDoOrdersInBasketByQuantity(
            @PathVariable("basketId") Long basketId,
            @PathVariable("bookId") Long bookId
    ) {
        return cartFacade.toDoOrdersInCartByQuantity(basketId, bookId);
    }

//===========================================Cart=====================================================================

    @GetMapping("/show/report/cartId/{cartId}")
    public List<ProductOrder> findDeliveryReportByCartId(@PathVariable("cartId") Long cartId) {
        return userFacade.findDeliveryReportByCartId(cartId);
    }

    @GetMapping("/show/archive/orders/cartId/{cartId}")
    public List<ProductOrder> findArchiveOrdersByCartId(@PathVariable("cartId") Long cartId) {
        return userFacade.findArchiveOrdersByCartId(cartId);
    }
}
