package com.iase24.springjunit.validator;


import com.iase24.springjunit.dto.CreateUserDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CreateUserValidatorTest {

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    @Test
    void shouldPassValidation() {
        CreateUserDTO createUserDTO = getCreateUserDto();

        ValidationResult actualResult = createUserValidator.validate(createUserDTO);

        assertFalse(actualResult.hasErrors());
    }

    @Test
    void invalidEmail() {
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .username("Foma")
                .email("fake")
                .password("123")
                .build();

        ValidationResult actualResult = createUserValidator.validate(createUserDTO);

        assertThat(actualResult.getErrors()).hasSize(1);
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.email");

    }

    private CreateUserDTO getCreateUserDto() {
        return CreateUserDTO.builder()
                .username("Foma")
                .email("fomabb@gmail.com")
                .password("123")
                .build();
    }
}