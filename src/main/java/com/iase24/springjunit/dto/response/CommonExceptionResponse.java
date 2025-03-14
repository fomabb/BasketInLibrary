package com.iase24.springjunit.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommonExceptionResponse {

    private LocalDateTime timeStamp;

    private String exceptionClass;

    private String message;
}
