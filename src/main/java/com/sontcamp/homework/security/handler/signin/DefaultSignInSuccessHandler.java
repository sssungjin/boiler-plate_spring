package com.sontcamp.homework.security.handler.signin;

import com.sontcamp.homework.dto.response.JwtTokenDto;
import com.sontcamp.homework.repository.UserRepository;
import com.sontcamp.homework.security.info.common.UserPrincipal;
import com.sontcamp.homework.utility.JwtUtil;
import com.sontcamp.homework.utility.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DefaultSignInSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 유저의 인증된 정보 들고오고
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        //소프트웨어의 식별 정보 (웹인지 앱인지)
        String userAgent = request.getHeader("User-Agent");

        JwtTokenDto jwtTokens = jwtUtil.generateJwtTokens(
                userPrincipal.getId(),
                userPrincipal.getRole()
        );

        userRepository.updateRefreshTokenAndLoginStatus(userPrincipal.getId(), jwtTokens.refreshToken(), true);

        if (userAgent != null && userAgent.contains("Dart")) {
            setSuccessAppResponse(response, jwtTokens);
        } else {
            setSuccessWebResponse(response, jwtTokens);
        }
    }

    // App은 redirect 못함
    private void setSuccessAppResponse(HttpServletResponse response, JwtTokenDto jwtTokenDto) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> result  = new HashMap<>();
        result.put("success", true);
        result.put("data", Map.of(
                "accessToken", jwtTokenDto.accessToken(),
                "refreshToken", jwtTokenDto.refreshToken()
        ));
        result.put("error", null);

        response.getWriter().write(JSONValue.toJSONString(result));
    }

    // Web은 redirect 가능
    private void setSuccessWebResponse(HttpServletResponse response, JwtTokenDto jwtTokenDto) throws IOException {
        final Cookie accessTokenCookie = new Cookie("accessToken", jwtTokenDto.accessToken());
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);

        CookieUtil.addCookie(response, "refreshToken", jwtTokenDto.refreshToken());
        response.sendRedirect("http://localhost:5173");
    }
}

