package com.iase24.springjunit.exceptionhandler.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
