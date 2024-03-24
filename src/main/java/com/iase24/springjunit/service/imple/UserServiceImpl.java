package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.service.BookService;
import com.iase24.springjunit.service.CartService;
import com.iase24.springjunit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getBasketById(Long id) {

        return userRepository.findById(id);
    }

    @Override
    public void createNewUser(User user) {

        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}
