package com.sontcamp.homework.service;

import com.sontcamp.homework.dto.response.UserDetailDto;
import com.sontcamp.homework.repository.UserMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.exception.ErrorCode;
import com.sontcamp.homework.domain.User;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMemoryRepository userMemoryRepository;

    public UserDetailDto readUser(Long userId) {
        User user = userMemoryRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        return UserDetailDto.fromEntity(user);
    }

}
