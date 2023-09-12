package com.api.global.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,  "서버오류"),
    INPUT_INVALID_ERROR(HttpStatus.BAD_REQUEST,  "잘못된 입력"),
    ENTITY_NOT_FOUND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않은 접근입니다. ");

    private final HttpStatus status;
    private final String message;
}