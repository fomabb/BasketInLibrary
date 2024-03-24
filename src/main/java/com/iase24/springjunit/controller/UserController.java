package com.iase24.springjunit.controller;

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
    public User createNewUser(
            @RequestBody User user
    ) {

        userService.createNewUser(user);

        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getBasketById(@PathVariable("id") Long id) {

        return userService.getBasketById(id);
    }
}
