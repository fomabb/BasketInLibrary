package com.iase24.springjunit.security.controller;

import com.iase24.springjunit.security.dto.JwtRequest;
import com.iase24.springjunit.security.dto.RegistrationUserDTO;
import com.iase24.springjunit.security.facade.AuthFacade;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Аутентификация и регистрация", description = "API для управлением безопасности")
public class AuthController {

    private final AuthFacade authFacade;

    @Operation(
            summary = "Вход в приложение.",
            description = """
                    ```В теле запроса необходимо прописать имя и пароль пользователя.```
                    """
    )
    @PostMapping("/sign-in")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authFacade.createAuthToken(authRequest);
    }

    @Operation(
            summary = "Регистрация нового пользователя.",
            description = """
                    ```В теле запроса необходимо ввести все необходимые для регистрации данные о пользователе.```
                    """
    )
    @PostMapping("/sign-up")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO) {
        return authFacade.createNewUser(registrationUserDTO);
    }

    //TODO
    @Hidden
    @PutMapping("/updateRole/{userId}")
    public ResponseEntity<?> updateUserRole(@PathVariable("userId") Long userId) {
        return authFacade.updateRoleUser(userId);
    }
}
