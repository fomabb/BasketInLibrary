package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.FaqQuestionDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
@Valid
public class UserController {

    private final UserFacade userFacade;

    @PostMapping
    public CreateUserDTO createNewUser(@RequestBody CreateUserDTO createUserDTO) {
        return userFacade.createNewUser(createUserDTO);
    }

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

    @PutMapping("/cartId/{cartId}/bookId/{bookId}")
    public Cart addBookInCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("bookId") Long bookId
    ) {
        return userFacade.addBookInCart(cartId, bookId);
    }

    @GetMapping("/cartId/{cartId}")
    public Cart getCartById(@PathVariable("cartId") Long cartId) {

        return userFacade.getCartById(cartId);
    }

    @DeleteMapping("cartId/{cartId}/bookId/{bookId}")
    public ResponseEntity<?> removeFromCart(
            @PathVariable Long cartId,
            @PathVariable Long bookId
    ) {
        return userFacade.removeFromCart(cartId, bookId);
//        return ResponseEntity.ok().build();
    }

//===========================================FAQ========================================================================

    @PostMapping("/faq/question/{categoryId}")
    public FaqQuestionDTO questionCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody FaqQuestionDTO question
    ) {
        return userFacade.questionCategory(categoryId, question);
    }

    @PutMapping("/faq/update/faqId/{faqId}")
    public FaqQuestionDTO updateQuestion(
            @PathVariable("faqId") Long faqId,
            @RequestBody FaqQuestionDTO question
    ) {
        return userFacade.updateQuestion(faqId, question);
    }

    @DeleteMapping("/faq/categoryId/{categoryId}/faqId/{faqId}")
    public ResponseEntity<String> removeFaqFromCategory(
            @PathVariable("categoryId") Long categoryId,
            @PathVariable("faqId") Long faqId
    ) {
        return userFacade.removeFaqFromCategory(categoryId, faqId);
    }
}
