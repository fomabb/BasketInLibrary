package com.iase24.springjunit.controller;

import com.iase24.springjunit.component.BookResponse;
import com.iase24.springjunit.component.PaginationInfo;
import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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

    private final AdminService adminService;
    private final UserService userService;
    private final CartService cartService;
    private final BookService bookService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Hello Admin!", HttpStatus.OK);
    }

    @PutMapping("/{faqId}")
    public FaqAnswerDTO answerForFaq(
            @PathVariable("faqId") Long faqId,
            @RequestBody FaqAnswerDTO answer
    ) {
        adminService.answerForFaq(faqId, answer);

        return answer;
    }

    @DeleteMapping("/categoryId/{categoryId}/faqId/{faqId}")
    public ResponseEntity<String> deleteFaq(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("faqId") Long faqId
    ) {
        adminService.deleteFaq(categoryId, faqId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Faq with ID " + faqId + " successfully deleted from category with ID " + categoryId);
    }

    @PostMapping("/description")
    public DescriptionCategory createDescriptionCategory(@RequestBody DescriptionCategory descriptionCategory) {

        adminService.createDescriptionCategory(descriptionCategory);

        return descriptionCategory;
    }

//=======================================================Book===========================================================

    @PostMapping("/newBooks")
    public List<Book> createNewBook(@RequestBody List<Book> book) {
        bookService.createNewBook(book);

        return book;
    }

    @PutMapping("/bookCount/{id}")
    public BookUpdateDTO updateBookCount(
            @PathVariable("id") Long id,
            @RequestBody BookUpdateDTO bookUpdateDTO
    ) {
        if (id != null) {
            bookService.updateBookCount(id, bookUpdateDTO);
        }

        return bookUpdateDTO;
    }

//=======================================================User===========================================================

    @GetMapping("/allUsers")
    public List<UserDataDTO> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/cartByUser")
    public Cart getCartByUser(@RequestParam("username") String username) {

        return cartService.getCartByLogin(username);
    }

    @GetMapping("/user/{id}")
    public Optional<UserDataDTO> getUserById(@PathVariable("id") Long id) {

        return userService.getUserById(id);
    }

//=======================================================Cart===========================================================

    @GetMapping("/allCarts")
    public List<Cart> getCarts() {

        return cartService.getCarts();
    }

//=======================================================Tree===========================================================

    @PostMapping("/createCategory")
    public List<Node> createNewCategory(@RequestBody List<Node> node) {

        bookService.createNewCategory(node);

        return node;
    }

    @PutMapping("/addChildrenId/{childrenId}")
    public Node addChildrenIdInParentId(
            @PathVariable("childrenId") Long childrenId,
            @RequestParam Node parentNode
    ) {
        bookService.addChildrenIdInParentId(childrenId, parentNode);

        return parentNode;
    }

    @PutMapping("/addBookId/{bookId}/categoryId/{categoryId}")
    public Node addBookInCategory(
            @PathVariable("bookId") Long bookId,
            @PathVariable("categoryId") Node categoryId
    ) {
        bookService.addBookInCategory(bookId, categoryId);

        return categoryId;
    }
}
