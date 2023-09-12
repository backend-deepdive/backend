package com.api.global.advice;


import com.api.global.advice.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleRuntimeException(BusinessException e) {
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse response =
                ErrorResponse.builder()
                        .httpStatus(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .build();
        log.warn(e.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = new ErrorResponse(BAD_REQUEST, e.getMessage());
        log.warn(e.getMessage());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }
}