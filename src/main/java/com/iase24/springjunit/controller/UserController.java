package com.iase24.springjunit.controller;

import com.iase24.springjunit.dto.BooCartDataDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.BookCart;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.repository.BookCartRepository;
import com.iase24.springjunit.service.BookCartService;
import com.iase24.springjunit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserDataDTO> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDataDTO getUserById(@PathVariable("id") Long id) {

        return userService.getUserById(id);
    }


}
