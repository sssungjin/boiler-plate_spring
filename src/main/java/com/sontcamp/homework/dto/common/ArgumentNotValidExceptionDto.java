package com.sontcamp.homework.dto.common;

import com.sontcamp.homework.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ArgumentNotValidExceptionDto extends ExceptionDto {
    private final MethodArgumentNotValidException methodArgumentNotValidException;
    @Getter
    private final Map<String, String> errorFields;

    public ArgumentNotValidExceptionDto(MethodArgumentNotValidException methodArgumentNotValidException) {
        super(ErrorCode.INVALID_ARGUMENT);
        this.methodArgumentNotValidException = methodArgumentNotValidException;
        this.errorFields = new HashMap<>();

        for (ObjectError objectError : methodArgumentNotValidException.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) objectError;
            errorFields.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
