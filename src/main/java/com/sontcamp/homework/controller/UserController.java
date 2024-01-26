package com.sontcamp.homework.controller;

import com.sontcamp.homework.annotation.UserId;
import com.sontcamp.homework.dto.common.ResponseDto;
import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.exception.ErrorCode;
import com.sontcamp.homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    //로그인 된 유저 정보 확인
    @GetMapping("")
    public ResponseDto<?> readUser(@UserId Long userId) {
        return ResponseDto.ok(userService.readUser(userId));
    }
}
