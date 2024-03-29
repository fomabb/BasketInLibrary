package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.UserDataDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDataDTO> login(String email, String password);

    Optional<UserDataDTO> getUserById(Long id);

    UserDataDTO createNewUser(CreateUserDTO createUserDTO);

    List<UserDataDTO> getAllUsers();

    Optional<UserDataDTO> getCartByUserId(Long userId);
}
