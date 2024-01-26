package com.sontcamp.homework.security.config;

import com.sontcamp.homework.security.filter.ExceptionFilter;
import com.sontcamp.homework.security.filter.JwtAuthenticationFilter;
import com.sontcamp.homework.security.handler.exception.CustomAccessDeniedHandler;
import com.sontcamp.homework.security.handler.exception.CustomAuthEntryPoint;
import com.sontcamp.homework.security.handler.signin.DefaultSignInFailureHandler;
import com.sontcamp.homework.security.handler.signin.DefaultSignInSuccessHandler;
import com.sontcamp.homework.security.handler.signin.OAuth2SignInFailureHandler;
import com.sontcamp.homework.security.handler.signin.OAuth2SignInSuccessHandler;
import com.sontcamp.homework.security.handler.signout.CustomSignOutProcessHandler;
import com.sontcamp.homework.security.handler.signout.CustomSignOutResultHandler;
import com.sontcamp.homework.security.provider.JwtProviderManager;
import com.sontcamp.homework.security.service.CustomOAuth2UserService;
import com.sontcamp.homework.security.service.CustomUserDetailService;
import com.sontcamp.homework.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DefaultSignInSuccessHandler defaultSignInSuccessHandler;
    private final DefaultSignInFailureHandler defaultSignInFailureHandler;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailService customUserDetailService;

    private final OAuth2SignInSuccessHandler oAuth2SignInSuccessHandler;
    private final OAuth2SignInFailureHandler oAuth2SignInFailureHandler;

    private final CustomSignOutProcessHandler customSignOutProcessHandler;
    private final CustomSignOutResultHandler customSignOutResultHandler;

    private final CustomAuthEntryPoint customAuthEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtProviderManager jwtproviderManager;

    private final JwtUtil jwtUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //CSRF 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                //기본 HTTP 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                //세션 관리: 상태를 유지하지 않는 세션 정책 설정
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //일단 모두 허용
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                //폼 기반 로그인 설정
                .formLogin(configurer ->
                        configurer
                                .loginPage("/login")                    //로그인 페이지 설정
                                .loginProcessingUrl("/auth/sign-in")    // 로그인 처리 URL
                                .usernameParameter("serialId")          //username을 custom
                                .passwordParameter("password")          //가독성을 위해 password 포함
                                .successHandler(defaultSignInSuccessHandler)
                                .failureHandler(defaultSignInFailureHandler)
                )//.userDetailsService(customUserDetailService)
                .oauth2Login(configurer ->
                                configurer
                                        .successHandler(oAuth2SignInSuccessHandler)
                                        .failureHandler(oAuth2SignInFailureHandler)
                                        .userInfoEndpoint(userInfoEndpoint ->
                                                userInfoEndpoint.userService(customOAuth2UserService)
                                        )
                )
//                //로그아웃 설정
                .logout(configurer ->
                        configurer
                                .logoutUrl("/auth/logout")
                                .addLogoutHandler(customSignOutProcessHandler)
                                .logoutSuccessHandler(customSignOutResultHandler)
                                .deleteCookies("JSESSIONID")
                )
//                //예외처리 설정
                .exceptionHandling(configurer ->
                        configurer
                                .authenticationEntryPoint(customAuthEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, jwtproviderManager),
                        LogoutFilter.class)
                .addFilterBefore(new ExceptionFilter(),
                        JwtAuthenticationFilter.class)
//                //SecurityFilterChain 빈을 반환
                .getOrBuild();
    }
}
