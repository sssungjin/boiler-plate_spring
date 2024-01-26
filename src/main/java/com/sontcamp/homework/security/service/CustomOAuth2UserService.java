package com.sontcamp.homework.security.service;

import com.sontcamp.homework.domain.User;
import com.sontcamp.homework.dto.response.JwtTokenDto;
import com.sontcamp.homework.dto.type.EProvider;
import com.sontcamp.homework.dto.type.ERole;
import com.sontcamp.homework.dto.type.factory.EProviderFactory;
import com.sontcamp.homework.repository.UserRepository;
import com.sontcamp.homework.security.info.common.UserPrincipal;
import com.sontcamp.homework.security.info.factory.OAuth2UserInfo;
import com.sontcamp.homework.security.info.factory.OAuth2UserInfoFactory;
import com.sontcamp.homework.utility.JwtUtil;
import com.sontcamp.homework.utility.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            return process(userRequest, super.loadUser(userRequest));
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    public OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User){
        EProvider provider = EProviderFactory.of(userRequest.getClientRegistration().getRegistrationId().toUpperCase(Locale.ROOT));
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider,oAuth2User.getAttributes());

        UserRepository.UserSecurityForm userSecurityForm = userRepository.findFormBySerialIdAndProvider(userInfo.getId(), provider)
                .orElseGet(()->
                {
                    User user = userRepository.save(
                            new User(
                                    userInfo.getId(),
                                    bCryptPasswordEncoder.encode(PasswordUtil.generateRandomPassword()),
                                    ERole.GUEST,
                                    provider
                            )
                    );
                    return UserRepository.UserSecurityForm.invoke(user);
                });

        return UserPrincipal.create(userSecurityForm,oAuth2User.getAttributes());
    }

    public JwtTokenDto processUserLogin(Long userId, String role) {
        JwtTokenDto jwtTokenDto = jwtUtil.generateJwtTokens(userId, ERole.valueOf(role));
        userRepository.updateRefreshTokenAndLoginStatus(userId, jwtTokenDto.refreshToken(), true);
        return jwtTokenDto;
    }

}
