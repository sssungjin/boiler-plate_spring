package com.sontcamp.homework.dto.common;


import com.sontcamp.homework.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ExceptionDto {
    private final int code;
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}