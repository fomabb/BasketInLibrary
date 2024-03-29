package com.iase24.springjunit.validator;

public interface Validator<T> {

    ValidationResult validate(T object);
}
