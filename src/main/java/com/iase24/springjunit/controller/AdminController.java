package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Node;
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
     * Метод для ответа на заданный пользователем вопрос
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
     * Метод удаления вопросов и ответов
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
     * Метод установки админом описания категории, внутри Node
     * @return JSON description
     */
    @PostMapping("/description")
    public DescriptionCategory createDescriptionCategory(@RequestBody DescriptionCategory descriptionCategory) {
        return adminFacade.createDescriptionCategory(descriptionCategory);
    }

//=======================================================Book===========================================================

    /**
     * Метод добавления новых книг на склад
     * @return JSON created books
     */
    @PostMapping("/newBooks")
    public List<Book> createNewBook(@RequestBody List<Book> book) {
        return adminFacade.createNewBook(book);
    }

    /**
     * Метод добавления колличества книги на складе
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
     * Показать всех зарегистрированных пользователей
     * @return JSON all user
     */
    @GetMapping("/allUsers")
    public List<UserDataDTO> getAllUsers() {
        return adminFacade.getAllUsers();
    }

    @GetMapping("/cartByUser")
    public Cart getCartByUser(@RequestParam("username") String username) {
        return adminFacade.getCartByUser(username);
    }

    @GetMapping("/user/{id}")
    public Optional<UserDataDTO> getUserById(@PathVariable("id") Long id) {
        return adminFacade.getUserById(id);
    }

//=======================================================Cart===========================================================

    @GetMapping("/allCarts")
    public List<Cart> getCarts() {
        return adminFacade.getCarts();
    }

//=======================================================Tree===========================================================

    @PostMapping("/createCategory")
    public List<Node> createNewCategory(@RequestBody List<Node> node) {

        return adminFacade.createNewCategory(node);
    }

    @PutMapping("/addChildrenId/{childrenId}")
    public Node addChildrenIdInParentId(
            @PathVariable("childrenId") Long childrenId,
            @RequestParam Node parentNode
    ) {
        return adminFacade.addChildrenIdInParentId(childrenId, parentNode);
    }

    @PutMapping("/addBookId/{bookId}/categoryId/{categoryId}")
    public Node addBookInCategory(
            @PathVariable("bookId") Long bookId,
            @PathVariable("categoryId") Node categoryId
    ) {
        return adminFacade.addBookInCategory(bookId, categoryId);
    }
}
