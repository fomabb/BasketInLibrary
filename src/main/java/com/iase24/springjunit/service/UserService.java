package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;

import java.util.List;

public interface UserService {
    UserDataDTO getUserById(Long id);

    void createNewUser(User user);

    List<UserDataDTO> getAllUsers();
}
