package com.api.global.advice.exception;


import com.api.global.advice.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND_ERROR);
    }
}
