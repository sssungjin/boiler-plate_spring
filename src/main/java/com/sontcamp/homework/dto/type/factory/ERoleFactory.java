package com.sontcamp.homework.dto.type.factory;

import com.sontcamp.homework.dto.type.ERole;
import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.exception.ErrorCode;

public class ERoleFactory {
    public static ERole of(String role) {
        return switch (role) {
            case "USER" -> ERole.USER;
            case "ADMIN" -> ERole.ADMIN;
            case "GUEST" -> ERole.GUEST;
            default -> throw new CommonException(ErrorCode.INVALID_ROLE);
        };
    }
}
