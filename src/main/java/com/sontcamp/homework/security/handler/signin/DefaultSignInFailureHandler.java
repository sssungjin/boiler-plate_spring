package com.sontcamp.homework.security.handler.signin;

import com.sontcamp.homework.security.handler.AbstractAuthenticationFailure;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.sontcamp.homework.exception.ErrorCode;

import java.io.IOException;
import java.util.Map;

@Component
public class DefaultSignInFailureHandler extends AbstractAuthenticationFailure implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        //setFailureAppResponse(response);
        setErrorResponse(response, ErrorCode.FAILURE_LOGIN);
        response.sendRedirect("http://localhost:5173/failure");
    }
}
