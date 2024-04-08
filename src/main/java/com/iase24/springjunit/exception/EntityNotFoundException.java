package com.iase24.springjunit.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String errorCode) {
        super(errorCode);
    }
}
