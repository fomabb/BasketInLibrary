package com.iase24.springjunit.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    NOT_EXISTING_USER("4000"),
    NOT_EXISTING_BOOK("4001"),
    NOT_EXISTING_CARD("4002"),
    NOT_EXISTING_LOGIN("4008"),
    NOT_EXISTING_EMAIL("4009");

    private final String errorCode;

    ExceptionCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
