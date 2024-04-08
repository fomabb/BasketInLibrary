package com.iase24.springjunit.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> entityNotFoundHandler(EntityNotFoundException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode(e.getMessage());
        String message = messageSource.getMessage(e.getMessage(), null, request.getLocale());
        response.setMessage(message);
        log.error(" Not found ", e);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponse> sqlExceptionHandler(SQLException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode("4003");
        response.setMessage(messageSource.getMessage("4003", null, request.getLocale()));
        log.error("SQL exception", e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionResponse> entityAlreadyExistHandler(DuplicateKeyException e, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(e.getClass().getSimpleName());
        response.setErrorCode("4004");
        response.setMessage(messageSource.getMessage("4004", null, request.getLocale()));
        log.error("ALREADY EXIST EXCEPTION: ", e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ExceptionResponse> responses = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ExceptionResponse(error.getField() + " " + error.getDefaultMessage(),
                        null, null)).collect(Collectors.toList());
        log.error("BAD_REQUEST", ex);
        return new ResponseEntity<>(responses, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        response.setErrorCode("4005");
        response.setMessage(messageSource.getMessage("4005", null, request.getLocale()));
        log.error("BAD_REQUEST", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage("error");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        response.setErrorCode("4006");
        response.setMessage(messageSource.getMessage("4006", null, request.getLocale()));
        log.error("BAD_REQUEST", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setException(ex.getClass().getSimpleName());
        response.setErrorCode("4007");
        response.setMessage(messageSource.getMessage("4007", null, request.getLocale()));
        log.error("NOT_ALLOWED", ex);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintHandle(ConstraintViolationException ex) {
        List<ExceptionResponse> responses = ex.getConstraintViolations()
                .stream()
                .map(error -> new ExceptionResponse(error.getMessage(),
                        null, null))
                .collect(Collectors.toList());
        log.info(" Validation error ", ex);
        return new ResponseEntity<>(responses, HttpStatus.BAD_REQUEST);
    }
}
