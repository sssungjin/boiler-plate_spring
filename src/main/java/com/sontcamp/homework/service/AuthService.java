package com.sontcamp.homework.service;

import com.sontcamp.homework.domain.User;
import com.sontcamp.homework.dto.request.AuthSignUpDto;
import com.sontcamp.homework.dto.request.OAuth2SignUpDto;
import com.sontcamp.homework.dto.response.JwtTokenDto;
import com.sontcamp.homework.dto.type.ERole;
import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.repository.UserRepository;
import com.sontcamp.homework.utility.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.sontcamp.homework.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public void authSignUp(AuthSignUpDto authSignUpDto) {
        userRepository.findBySerialId(authSignUpDto.serialId())
                .ifPresent(user -> {
                    throw new CommonException(ErrorCode.DUPLICATED_SERIAL_ID);
                });

        userRepository.save(
                User.fromSignUpJson(
                        authSignUpDto,
                        bCryptPasswordEncoder.encode(authSignUpDto.password())
                )
        );
    }

    @Transactional
    public JwtTokenDto oauth2SignUp(Long userId, OAuth2SignUpDto oAuth2SignUpDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        log.info("userId: {}", userId);

        user.registerComplete(oAuth2SignUpDto.name(), oAuth2SignUpDto.phoneNumber());
        final JwtTokenDto jwtTokenDto = jwtUtil.generateJwtTokens(user.getId(), user.getRole());
        user.updateRefreshToken(jwtTokenDto.refreshToken());
        return jwtTokenDto;
    }

    @Transactional
    public void logout(Long userId) {
        User user =  userRepository
                .findUserByUserIdAndIsLoginAndRefreshTokenIsNotNull(userId)
                .orElseThrow(
                        () -> new CommonException(ErrorCode.NOT_FOUND_USER));
        user.logoutUser();
    }


    @Transactional
    public void withDrawlUser(Long userId) {
        User user =  userRepository
                .findUserByUserIdAndIsLoginAndRefreshTokenIsNotNull(userId)
                .orElseThrow(
                        () -> new CommonException(ErrorCode.NOT_FOUND_USER));
        userRepository.delete(user);
    }
}
