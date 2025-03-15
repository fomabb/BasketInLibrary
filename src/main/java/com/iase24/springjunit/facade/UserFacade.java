package com.iase24.springjunit.facade;

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

    //TODO
    public Optional<UserDataDTO> getCartByUserId(Long userId) {
        return userService.getCartByUserId(userId);
    }

//===========================================FAQ========================================================================

    public ResponseEntity<String> removeFaqFromCategory(Long categoryId, Long faqId) {
        userService.removeFaqFromCategory(categoryId, faqId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Faq with ID " + faqId + " successfully deleted from category with ID " + categoryId);
    }
}
