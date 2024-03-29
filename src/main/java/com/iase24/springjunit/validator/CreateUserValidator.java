package com.iase24.springjunit.validator;

import com.iase24.springjunit.dto.CreateUserDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateUserValidator implements Validator<CreateUserDTO> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(CreateUserDTO object) {
        var validationResult = new ValidationResult();

        if (object.getLogin().isEmpty()) {
            validationResult.add(Error.of("invalid.login", "Login is invalid"));
        }

        if (object.getEmail().isEmpty()) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }

        if (object.getPassword().isEmpty()) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }

        return validationResult;
    }
}
