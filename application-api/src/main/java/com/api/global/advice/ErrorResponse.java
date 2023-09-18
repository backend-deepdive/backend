package com.api.global.advice;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String message;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ErrorResponse(ErrorCode code) {
        this.httpStatus = code.getStatus();
        this.message = code.getMessage();
    }
}

