package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.UserDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDataDTO> login(String email, String password);

    Optional<UserDataDTO> getUserById(Long id);

    void createNewUser(User user);

    List<UserDataDTO> getAllUsers();
}
