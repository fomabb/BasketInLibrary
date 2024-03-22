package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.UserService;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Override
    public Optional<User> getBasketById(Long id) {

        return userRepository.findById(id);
    }
}
