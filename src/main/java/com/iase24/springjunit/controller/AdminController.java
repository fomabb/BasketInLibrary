package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.*;
import com.iase24.springjunit.entities.*;
import com.iase24.springjunit.facade.AdminFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/admin/")
@RequiredArgsConstructor
@Valid
public class AdminController {

    private final AdminFacade adminFacade;

    /**
     * Добавление коментария на заданный пользователем вопрос
     *
     * @return JSON answer
     */
    @PutMapping("/answer/faqId/{faqId}")
    public FaqAnswerDTO answerForFaq(
            @PathVariable("faqId") Long faqId,
            @RequestBody FaqAnswerDTO answer
    ) {
        return adminFacade.answerForFaq(faqId, answer);
    }

    /**
     * Удаление вопросов и ответов
     *
     * @return Response entity ok
     */
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
    @PostMapping("/create/descriptionByName")
    public ResponseEntity<String> createDescriptionByCategoryName(
            @RequestParam("categoryName") String categoryName,
            @RequestBody DescriptionDataDTO descriptionCategory
    ) {
        return adminFacade.createDescriptionByCategoryName(categoryName, descriptionCategory);
    }

//=======================================================Book===========================================================

    /**
     * Добавление новых книг на склад
     *
     * @return JSON created books
     */
    @PostMapping("/newBooks")
    public List<Book> createNewBook(@RequestBody List<Book> book) {
        return adminFacade.createNewBook(book);
    }

    /**
     * Добавление колличества книги на складе
     *
     * @return bookUpdateDTO
     */
    @PutMapping("/bookCount/{id}")
    public BookUpdateDTO updateBookCount(
            @PathVariable("id") Long id,
            @RequestBody BookUpdateDTO bookUpdateDTO
    ) {
        return adminFacade.updateBookCount(id, bookUpdateDTO);
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
    public Cart getCartByUser(@RequestParam("username") String username) {
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

//=======================================================Cart===========================================================

    /**
     * Показать все зарегистрированные заказы
     *
     * @return all orders
     */
    @GetMapping("/allCarts")
    public List<Cart> getCarts() {
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
     * @return JSON
     */
    @PutMapping("/addChildrenId/{childrenId}")
    public Node addChildrenIdInParentId(
            @PathVariable("childrenId") Long childrenId,
            @RequestParam Node parentNode
    ) {
        return adminFacade.addChildrenIdInParentId(childrenId, parentNode);
    }

    /**
     * Добавление книг в категорию
     *
     * @return JSON
     */
    @PutMapping("/addBookId/{bookId}/categoryId/{categoryId}")
    public Node addBookInCategory(
            @PathVariable("bookId") Long bookId,
            @PathVariable("categoryId") Node categoryId
    ) {
        return adminFacade.addBookInCategory(bookId, categoryId);
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

//=======================================================BookCart=======================================================

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
