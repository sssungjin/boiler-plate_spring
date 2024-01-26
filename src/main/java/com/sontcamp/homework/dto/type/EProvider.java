package com.sontcamp.homework.dto.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EProvider {
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    DEFAULT("DEFAULT");

    private final String provider;

    @Override
    public String toString() {
        return provider;
    }
}
