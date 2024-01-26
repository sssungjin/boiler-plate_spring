package com.sontcamp.homework.utility;

import com.sontcamp.homework.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import com.sontcamp.homework.exception.ErrorCode;

import java.util.Optional;

public class HeaderUtil {
    public static Optional<String> refineHeader(HttpServletRequest request, String headerName, String prefix){
        String headerValue = request.getHeader(headerName);
        if (!StringUtils.hasText(headerValue) || !headerValue.startsWith(prefix))
            throw new CommonException(ErrorCode.INVALID_AUTHENTICATION_HEADER);
        return Optional.of(headerValue.substring(prefix.length()));
    }
}
