package com.sontcamp.homework.security.service;

import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.exception.ErrorCode;
import com.sontcamp.homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.sontcamp.homework.security.info.common.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        String serialId;
        if (username != null) {
            serialId = username;
        } else {
            throw new CommonException(ErrorCode.MISSING_REQUEST_PARAMETER);
        }
        UserRepository.UserSecurityForm user = userRepository
                .findFormBySerialId(serialId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserPrincipal.create(user);
    }

    public UserPrincipal loadUserById(Long id) {
        UserRepository.UserSecurityForm user = userRepository
                .findFormById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return UserPrincipal.create(user);
    }
}
