package com.iase24.springjunit.exceptionhandler;

import com.iase24.springjunit.dto.response.CommonExceptionResponse;
import com.iase24.springjunit.exceptionhandler.exceptions.BusinessException;
import com.iase24.springjunit.exceptionhandler.exceptions.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.time.LocalDateTime.now;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CommonExceptionResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CommonExceptionResponse> handleValidationException(ValidationException e) {
        return ResponseEntity.unprocessableEntity()
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonExceptionResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.unprocessableEntity()
                .body(buildResponseBody(e.getMessage(), e.getClass().getSimpleName()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleRuntimeException() {
        return ResponseEntity.internalServerError().build();
    }

    private CommonExceptionResponse buildResponseBody(String message, String exceptionClass) {
        return CommonExceptionResponse.builder()
                .timeStamp(now())
                .exceptionClass(exceptionClass)
                .message(message)
                .build();
    }
}
