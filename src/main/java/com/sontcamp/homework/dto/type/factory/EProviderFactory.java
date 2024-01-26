package com.sontcamp.homework.dto.type.factory;

import com.sontcamp.homework.dto.type.EProvider;
import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.exception.ErrorCode;

public class EProviderFactory {
    public static EProvider of(String provider) {
        return switch (provider) {
            case "KAKAO" -> EProvider.KAKAO;
            case "DEFAULT" -> EProvider.DEFAULT;
            default -> throw new CommonException(ErrorCode.INVALID_PROVIDER);
        };
    }
}
