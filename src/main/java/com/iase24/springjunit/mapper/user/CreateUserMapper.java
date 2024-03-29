package com.iase24.springjunit.mapper.user;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.mapper.Mapper;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@NoArgsConstructor(access = PRIVATE)
public class CreateUserMapper implements Mapper<CreateUserDTO, User> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User map(CreateUserDTO object) {
        return User.builder()
                .login(object.getLogin())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }
}

