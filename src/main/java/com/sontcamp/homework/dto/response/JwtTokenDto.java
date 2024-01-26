package com.sontcamp.homework.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public record JwtTokenDto (
        @JsonProperty("access_token")
        @NotNull
        String accessToken,

        @NotNull
        @JsonProperty("refresh_token")
        String refreshToken
) {
    @Builder
    public JwtTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
