package com.iase24.springjunit.service;

import com.iase24.springjunit.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getBasketById(Long id);

    void createNewUser(User user);

    List<User> getAllUsers();
}
