package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.FaqQuestionDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.service.imple.CartServiceImpl;
import com.iase24.springjunit.service.imple.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
@Valid
public class UserController {

    private final UserServiceImpl userService;
    private final CartServiceImpl cartService;

    @PostMapping
    public CreateUserDTO createNewUser(@RequestBody CreateUserDTO createUserDTO) {

        userService.createNewUser(createUserDTO);

        return createUserDTO;
    }


    @GetMapping("/{id}")
    public Optional<UserDataDTO> getUserById(@PathVariable("id") Long id) {

        return userService.getUserById(id);
    }

    //TODO
    @GetMapping("/cart/userId/{userId}")
    public Optional<UserDataDTO> getCartByUserId(
            @PathVariable("userId") Long userId
    ) {

        return userService.getCartByUserId(userId);
    }

//===========================================Cart=======================================================================

    @PutMapping("/cartId/{cartId}/bookId/{bookId}")
    public ResponseEntity<?> addBookInCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
    ) {
        return cartService.addBookInCart(cartId, bookId);
    }

    @GetMapping("/cartId/{cartId}")
    public Cart getCartById(@PathVariable("cartId") Long cartId) {

        return cartService.getCartById(cartId);
    }

    @DeleteMapping("cartId/{cartId}/bookId/{bookId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long cartId,
            @PathVariable Long bookId
    ) {
        return cartService.removeFromCart(cartId, bookId);

//        return ResponseEntity.ok().build();
    }

//===========================================FAQ========================================================================

    @PostMapping("/faq/question/{categoryId}")
    public FaqQuestionDTO questionCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody FaqQuestionDTO question
    ) {
        userService.questionCategory(categoryId, question);

        return question;
    }

    @PutMapping("/faq/update/faqId/{faqId}")
    public FaqQuestionDTO updateQuestion(
            @PathVariable("faqId") Long faqId,
            @RequestBody FaqQuestionDTO question
    ) {
        userService.updateQuestion(faqId, question);

        return question;
    }

    @DeleteMapping("/faq/categoryId/{categoryId}/faqId/{faqId}")
    public ResponseEntity<String> removeFaqFromCategory(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("faqId") Long faqId
    ) {
        userService.removeFaqFromCategory(categoryId, faqId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Faq with ID " + faqId + " successfully deleted from category with ID " + categoryId);
    }
}
