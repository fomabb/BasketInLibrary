package com.iase24.springjunit.mapper.user;

import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.mapper.Mapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Component
public class UserMapper implements Mapper<User, UserDataDTO> {

    private static final UserMapper INSTANCE = new UserMapper();

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public UserDataDTO map(User object) {
        return UserDataDTO.builder()
                .id(object.getId())
                .login(object.getLogin())
                .email(object.getEmail())
                .build();
    }
}
