package com.sontcamp.homework.security.info.jwt;

import com.sontcamp.homework.dto.type.ERole;
import lombok.Builder;

@Builder
public record JwtUserInfo (
        Long id,
        ERole role
) {

}
