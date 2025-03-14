package com.iase24.springjunit.exceptionhandler.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
