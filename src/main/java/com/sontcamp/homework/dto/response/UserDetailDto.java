package com.sontcamp.homework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sontcamp.homework.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public record UserDetailDto (
        @JsonProperty("id")
        Long id,

                @JsonProperty("name")
        String name,

        @JsonProperty("phone_number")
        String phoneNumber,

        @JsonProperty("created_at")
        String createdAt
) {
        public static UserDetailDto fromEntity(User user) {
        return new UserDetailDto(
                user.getId(),
                user.getNickname(),
                user.getPhoneNumber(),
                user.getCreatedAt().toString()
        );
    }
}
