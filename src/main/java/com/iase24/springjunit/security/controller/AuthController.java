package com.iase24.springjunit.security.controller;

import com.iase24.springjunit.security.dto.JwtRequest;
import com.iase24.springjunit.security.dto.RegistrationUserDTO;
import com.iase24.springjunit.security.facade.AuthFacade;
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
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authFacade.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO) {
        return authFacade.createNewUser(registrationUserDTO);
    }

    //TODO
    @PutMapping("/updateRole/{userId}")
    public ResponseEntity<?> updateUserRole(@PathVariable("userId") Long userId) {
        return authFacade.updateRoleUser(userId);
    }
}
