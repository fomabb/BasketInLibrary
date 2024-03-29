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
        return null;
    }
}
