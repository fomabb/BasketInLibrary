package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.exception.ValidationException;
import com.iase24.springjunit.mapper.user.CreateUserMapper;
import com.iase24.springjunit.mapper.user.UserMapper;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.service.UserService;
import com.iase24.springjunit.validator.CreateUserValidator;
import com.iase24.springjunit.validator.ValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CreateUserMapper createUserMapper;
    private final UserMapper userMapper;
    private final CreateUserValidator createUserValidator;

    @Override
    public Optional<UserDataDTO> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).map(userMapper::map);
    }

    @SneakyThrows
    @Override
    public UserDataDTO createNewUser(CreateUserDTO createUserDTO) {

        ValidationResult validationResult = createUserValidator.validate(createUserDTO);
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getErrors());
        }

        User userEntity = createUserMapper.map(createUserDTO);
        userEntity.setPassword(DigestUtils.md5DigestAsHex(userEntity.getPassword().getBytes()));

        userRepository.save(userEntity);

        return userMapper.map(userEntity);
    }

    @Override
    public Optional<UserDataDTO> getUserById(Long id) {

        return Optional.ofNullable(userRepository.findById(id)
                .map(userMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + id + " not found")));
    }

    @Override
    public List<UserDataDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDataDTO(user.getId(), user.getLogin(), user.getEmail()))
                .collect(Collectors.toList());
    }

    //    TODO
    @Override
    public Optional<UserDataDTO> getCartByUserId(Long userId) {

        return userRepository.findUserByIdAndCart(userId).map(userMapper::map);
    }
}
