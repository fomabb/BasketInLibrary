package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.exception.ValidationException;
import com.iase24.springjunit.mapper.user.CreateUserMapper;
import com.iase24.springjunit.mapper.user.UserMapper;
import com.iase24.springjunit.repository.UserRepository;
import com.iase24.springjunit.validator.CreateUserValidator;
import com.iase24.springjunit.validator.Error;
import com.iase24.springjunit.validator.ValidationResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private CreateUserValidator createUserValidator;
    @Mock
    private CreateUserMapper createUserMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loginSuccess() {
        User user = getUser();
        UserDataDTO userDataDTO = getUserDataDto();
        doReturn(Optional.of(user)).when(userRepository).findByEmailAndPassword(user.getEmail(), user.getPassword());
        doReturn(userDataDTO).when(userMapper).map(user);
        Optional<UserDataDTO> actualResult = userService.login(user.getEmail(), user.getPassword());
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(userDataDTO);
    }

    @Test
    void loginFailed() {
        doReturn(Optional.empty()).when(userRepository).findByEmailAndPassword(any(), any());
        Optional<UserDataDTO> actualResult = userService.login("dumy", "123");
        assertThat(actualResult).isEmpty();
        verifyNoInteractions(userMapper);
    }

    @Test
    void create() {
        CreateUserDTO createUserDto = getCreateUserDto();

        User user = getUser();
        UserDataDTO userDataDTO = getUserDataDto();

        doReturn(new ValidationResult()).when(createUserValidator).validate(createUserDto);
        doReturn(user).when(createUserMapper).map(createUserDto);
        doReturn(userDataDTO).when(userMapper).map(user);

        UserDataDTO actualResult = userService.createNewUser(createUserDto);

        assertThat(actualResult).isEqualTo(userDataDTO);

        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionIfDtoInvalid() {
        CreateUserDTO createUserDTO = getCreateUserDto();
        ValidationResult validationResult = new ValidationResult();

        validationResult.add(Error.of("invalid email", "massage"));
        doReturn(validationResult).when(createUserValidator).validate(createUserDTO);

        assertThrows(ValidationException.class, ()-> userService.createNewUser(createUserDTO));
        verifyNoInteractions(userRepository, createUserMapper, userMapper);
    }

    private User getUser() {
        return User.builder()
                .id(12L)
                .login("Ric")
                .email("rici@gmail.com")
                .password("123")
                .build();
    }

    private UserDataDTO getUserDataDto() {
        return UserDataDTO.builder()
                .id(12L)
                .login("Ric")
                .email("rici@gmail.com")
                .build();
    }

    private CreateUserDTO getCreateUserDto() {
        return CreateUserDTO.builder()
                .login("Foma")
                .email("foma@gmail.com")
                .password("123")
                .build();
    }
}