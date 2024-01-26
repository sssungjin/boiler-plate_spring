package com.sontcamp.homework.security.handler;

import com.sontcamp.homework.exception.ErrorCode;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;
import com.sontcamp.homework.dto.common.ExceptionDto;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAuthenticationFailure {
    protected void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode) throws IOException, java.io.IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());

        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("data", null);
        result.put("error", new ExceptionDto(errorCode));

        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
