package com.sontcamp.homework.security.handler.signin;

import com.sontcamp.homework.dto.response.JwtTokenDto;
import com.sontcamp.homework.security.info.common.UserPrincipal;
import com.sontcamp.homework.security.service.CustomOAuth2UserService;
import com.sontcamp.homework.utility.CookieUtil;
import com.sontcamp.homework.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SignInSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Transactional
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        JwtTokenDto jwtTokenDto = customOAuth2UserService.processUserLogin(userPrincipal.getId(), String.valueOf(userPrincipal.getRole()));

        CookieUtil.addSecureCookie(response, "refreshToken", jwtTokenDto.refreshToken(), jwtUtil.getRefreshTokenExpirePeriod());
        CookieUtil.addCookie(response, "accessToken", jwtTokenDto.accessToken());
        response.sendRedirect("http://localhost:5173");
    }
}
