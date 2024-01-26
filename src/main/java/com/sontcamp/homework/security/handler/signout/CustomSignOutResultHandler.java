package com.sontcamp.homework.security.handler.signout;

import com.sontcamp.homework.exception.ErrorCode;
import com.sontcamp.homework.security.handler.AbstractAuthenticationFailure;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomSignOutResultHandler extends AbstractAuthenticationFailure implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (authentication != null) {
            setSuccessResponse(response);
        }
        else {
            setErrorResponse(response, ErrorCode.UNAUTHORIZED);
        }
    }

    private void setSuccessResponse(HttpServletResponse response) throws IOException {
        Map<String, String> result = new HashMap<>();
        result.put("status", "success");
        result.put("message", "로그아웃이 성공적으로 처리되었습니다.");
        result.put("error", null);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(JSONValue.toJSONString(result));
    }
}
