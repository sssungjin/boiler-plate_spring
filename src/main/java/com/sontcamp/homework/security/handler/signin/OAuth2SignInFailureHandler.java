package com.sontcamp.homework.security.handler.signin;

import com.sontcamp.homework.exception.ErrorCode;
import com.sontcamp.homework.security.handler.AbstractAuthenticationFailure;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SignInFailureHandler extends AbstractAuthenticationFailure implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        setErrorResponse(response, ErrorCode.FAILURE_LOGIN);
        response.sendRedirect("http://localhost:5173/failure");
    }
}