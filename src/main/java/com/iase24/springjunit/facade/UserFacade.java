package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.FaqQuestionDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public CreateUserDTO createNewUser(CreateUserDTO createUserDTO) {
        userService.createNewUser(createUserDTO);
        return createUserDTO;
    }

    //TODO
    public Optional<UserDataDTO> getCartByUserId(Long userId) {
        return userService.getCartByUserId(userId);
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
}
