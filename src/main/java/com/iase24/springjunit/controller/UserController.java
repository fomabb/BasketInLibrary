package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public CreateUserDTO createNewUser(
            @RequestBody CreateUserDTO createUserDTO
    ) {

        userService.createNewUser(createUserDTO);

        return createUserDTO;
    }

    @GetMapping
    public List<UserDataDTO> getAllUsers() {

        return userService.getAllUsers();
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
}
