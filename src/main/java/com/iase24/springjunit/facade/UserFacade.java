package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.FaqQuestionDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.BookCart;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.exception.AppError;
import com.iase24.springjunit.service.BookCartService;
import com.iase24.springjunit.service.imple.CartServiceImpl;
import com.iase24.springjunit.service.imple.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserServiceImpl userService;
    private final CartServiceImpl cartService;
    private final BookCartService bookCartService;

    public CreateUserDTO createNewUser(CreateUserDTO createUserDTO) {
        userService.createNewUser(createUserDTO);
        return createUserDTO;
    }

    public Optional<UserDataDTO> getUserById(Long id) {
        return userService.getUserById(id);
    }

    //TODO
    public Optional<UserDataDTO> getCartByUserId(Long userId) {
        return userService.getCartByUserId(userId);
    }

//===========================================Cart=======================================================================

    public Cart addBookInCart(Long cartId, Long bookId) {
        return cartService.addBookInCart(cartId, bookId);
    }

    public Cart getCartById(Long cartId) {
        return cartService.getCartById(cartId);
    }

    public ResponseEntity<?> removeFromCart(Long cartId, Long bookId) {
        cartService.removeFromCart(cartId, bookId);
        return new ResponseEntity<>(
                "Book with id " + bookId + " remove in cart with id " + cartId
                , HttpStatus.OK
        );
    }

//===========================================FAQ========================================================================

    public FaqQuestionDTO questionCategory(Long categoryId, FaqQuestionDTO question) {
        userService.questionCategory(categoryId, question);
        return question;
    }

    public FaqQuestionDTO updateQuestion(Long faqId, FaqQuestionDTO question) {
        userService.updateQuestion(faqId, question);
        return question;
    }

    public ResponseEntity<String> removeFaqFromCategory(Long categoryId, Long faqId) {
        userService.removeFaqFromCategory(categoryId, faqId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Faq with ID " + faqId + " successfully deleted from category with ID " + categoryId);
    }

//===========================================Basket=====================================================================

    public List<BookCart> findDeliveryReportByCartId(Long cartId) {
        return bookCartService.findDeliveryReportByCartId(cartId);
    }

    public List<BookCart> findArchiveOrdersByCartId(Long cartId) {
        return bookCartService.findArchiveOrdersByCartId(cartId);
    }
}
