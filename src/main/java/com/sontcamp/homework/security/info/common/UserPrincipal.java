package com.sontcamp.homework.security.info.common;

import com.sontcamp.homework.dto.type.ERole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.sontcamp.homework.repository.UserRepository;


import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPrincipal implements OAuth2User, UserDetails {
    //implements Oidc 는 구글
    @Getter private final long id;
    @Getter private final ERole role;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    public static UserPrincipal create(UserRepository.UserSecurityForm userForm) {
        return UserPrincipal.builder()
                .id(userForm.getId())
                .role(userForm.getRole())
                .password(userForm.getPassword())
                .attributes(Collections.emptyMap())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(userForm.getRole().toString())))
                .build();

    }

    public static UserPrincipal create(UserRepository.UserSecurityForm userForm, Map<String, Object> attributes) {
        return UserPrincipal.builder()
                .id(userForm.getId())
                .role(userForm.getRole())
                .password(userForm.getPassword())
                .attributes(attributes)
                .authorities(Collections.singleton(new SimpleGrantedAuthority(userForm.getRole().toString())))
                .build();

    }

    // OAuth2User methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // UserDetails methods
    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
