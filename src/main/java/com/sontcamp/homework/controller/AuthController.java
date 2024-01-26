package com.sontcamp.homework.controller;

import com.sontcamp.homework.annotation.UserId;
import com.sontcamp.homework.dto.common.ResponseDto;
import com.sontcamp.homework.dto.request.AuthSignUpDto;
import com.sontcamp.homework.dto.request.OAuth2SignUpDto;
import com.sontcamp.homework.service.AuthService;
import com.sontcamp.homework.utility.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //기본 회원가입
    @PostMapping("/auth/sign-up")
    public ResponseDto<?> authSignUp(@RequestBody @Valid AuthSignUpDto authSignUpDto) {
        authService.authSignUp(authSignUpDto);
        return ResponseDto.created(null);
    }

    //소셜 회원가입
    @PatchMapping("/oauth2/sign-up")
    private ResponseDto<?> oauth2SignUp (
            @UserId Long userId, @RequestBody @Valid OAuth2SignUpDto authSignUpDto) {
        return ResponseDto.created(authService.oauth2SignUp(userId, authSignUpDto));
    }

    @DeleteMapping("/auth/withdrawal")
    public ResponseDto<?> withdrawalUser(@UserId Long userId) {
        authService.withDrawlUser(userId);
        return ResponseDto.ok(null);
    }
}
