package com.iase24.springjunit.mapper;

import com.iase24.springjunit.dto.CreateUserDTO;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.mapper.user.CreateUserMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CreateUserMapperTest {

    private final CreateUserMapper mapper = CreateUserMapper.getInstance();

    @Test
    void map() {
        CreateUserDTO dto = CreateUserDTO.builder()
                .username("Ric")
                .email("rici@gmail.com")
                .password("123")
                .build();


        User actualResult = mapper.map(dto);
        User expectedResult = User.builder()
                .username("Ric")
                .email("rici@gmail.com")
                .password("123")
                .build();

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}