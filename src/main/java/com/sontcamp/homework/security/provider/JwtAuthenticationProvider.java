package com.sontcamp.homework.security.provider;

import com.sontcamp.homework.dto.type.ERole;
import com.sontcamp.homework.security.info.common.UserPrincipal;
import com.sontcamp.homework.security.info.jwt.JwtAuthenticationToken;
import com.sontcamp.homework.security.info.jwt.JwtUserInfo;
import com.sontcamp.homework.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailService customUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserPrincipal userPrincipal = null;

        if (authentication instanceof JwtAuthenticationToken) {
            JwtUserInfo jwtUserInfo = new JwtUserInfo((Long) authentication.getPrincipal(), (ERole) authentication.getCredentials());
            userPrincipal = customUserDetailService.loadUserById(jwtUserInfo.id());

            if (userPrincipal.getRole() != jwtUserInfo.role()) {
                throw new AuthenticationException("Invalid Role") {
                };
            }
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            userPrincipal = (UserPrincipal) customUserDetailService.loadUserByUsername((String) authentication.getPrincipal());
        }

        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
