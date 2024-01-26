package com.sontcamp.homework.security.info.factory;

import com.sontcamp.homework.dto.type.EProvider;
import com.sontcamp.homework.exception.CommonException;
import com.sontcamp.homework.security.info.oauth.GoogleOAuth2UserInfo;
import com.sontcamp.homework.security.info.oauth.KakaoOAuth2UserInfo;
import com.sontcamp.homework.exception.ErrorCode;
import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(EProvider provider, Map<String, Object> attributes) {
        switch (provider) {
            case KAKAO -> {
                return new KakaoOAuth2UserInfo(attributes);
            }
            case GOOGLE -> {
                return new GoogleOAuth2UserInfo(attributes);
            }
            default -> {
                throw new CommonException(ErrorCode.BAD_REQUEST);
            }
        }
    }
}
