package com.sontcamp.homework.security.filter;

import com.sontcamp.homework.constant.Constants;
import com.sontcamp.homework.dto.type.ERole;
import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.exception.ErrorCode;
import com.sontcamp.homework.security.info.jwt.JwtAuthenticationToken;
import com.sontcamp.homework.security.info.jwt.JwtUserInfo;
import com.sontcamp.homework.security.provider.JwtProviderManager;
import com.sontcamp.homework.utility.HeaderUtil;
import com.sontcamp.homework.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final JwtProviderManager jwtProviderManager;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Constants.NO_NEED_AUTH_URLS.contains(request.getRequestURI());
                //|| request.getRequestURI().startsWith("/guest");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_AUTHENTICATION_HEADER));

        Claims claims = jwtUtil.validateToken(token);

        JwtUserInfo userInfo = new JwtUserInfo(
                Long.parseLong(claims.get(Constants.USER_ID_CLAIM_NAME, String.class)),
                ERole.valueOf(claims.get(Constants.USER_ROLE_CLAIM_NAME, String.class))
        );

        // 인증 전 객체
        JwtAuthenticationToken beforeAuthentication = new JwtAuthenticationToken(
                null,
                userInfo.id(),
                userInfo.role()
        );

        // 인증 후 객체 생성
        UsernamePasswordAuthenticationToken afterAuthentication = (UsernamePasswordAuthenticationToken) jwtProviderManager.authenticate(beforeAuthentication);

        afterAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 인증 객체를 SecurityContext에 저장
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(afterAuthentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }


}
