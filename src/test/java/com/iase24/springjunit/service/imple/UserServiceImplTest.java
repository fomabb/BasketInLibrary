package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.mapper.user.UserMapper;
import com.iase24.springjunit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

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
}