package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.*;
import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.facade.BasketFacade;
import com.iase24.springjunit.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
@Valid
public class UserController {

    private final UserFacade userFacade;
    private final BasketFacade basketFacade;

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

//===========================================Cart=======================================================================

    /**
     * Оформление заказа по ID товара
     */
    @PutMapping("/cartId/{cartId}/bookId/{bookId}")
    public Cart addBookInCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
    ) {
        return userFacade.addBookInCart(cartId, bookId);
    }

    /**
     * Найти заказ по ID
     *
     * @return cart
     */
    @GetMapping("/cartId/{cartId}")
    public Cart getCartById(@PathVariable("cartId") Long cartId) {

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

//===========================================Basket========================================================================

    /**
     * Найти корзину по ID пользователя
     *
     * @return basket with things
     */
    @GetMapping("/basketId/{id}")
    public Basket getBasketById(@PathVariable("id") Long id) {
        return basketFacade.getBasketById(id);
    }

    /**
     * Вывести все товары из корзины
     *
     * @return books
     */
    @GetMapping("/basket/allBooksInBasket/basketId/{basketId}")
    public List<BookInBasketDataDTO> getBooksInBasketById(@PathVariable("basketId") Long basketId) {
        return basketFacade.getBooksInBasketById(basketId);
    }

    /**
     * Добавление товара в корзину
     *
     * @return basket with things
     */
    @PostMapping("/addBookInBasket/basketId/{basketId}/bookId/{bookId}")
    public Basket createBasket(@PathVariable("basketId") Long basketId, @PathVariable("bookId") Long bookId) {
        return basketFacade.createBasket(basketId, bookId);
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
        return basketFacade.updateQuantity(basketId, bookId, updateBookQuantity);
    }

    /**
     * Удаление товара из корзины
     */
    @DeleteMapping("/removeBookInBasket/basketId/{basketId}/bookId/{bookId}")
    public ResponseEntity<?> removeBookInBasket(
            @PathVariable("basketId") Long basketId,
            @PathVariable("bookId") Long bookId
    ) {
        return basketFacade.removeBookInBasket(basketId, bookId);
    }

    /**
     * Сформировать заказ по колличеству товара
     *
     * @return cart
     */
    @PostMapping("/createOrdersByQuantityInBasket/basketId/{basketId}/bookId/{bookId}")
    public ResponseEntity<Book> toDoOrdersInBasketByQuantity(
            @PathVariable("basketId") Long basketId,
            @PathVariable("bookId") Long bookId
    ) {
        return basketFacade.toDoOrdersInBasketByQuantity(basketId, bookId);
    }
}
