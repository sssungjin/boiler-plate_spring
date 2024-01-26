package com.sontcamp.homework.domain;

import com.sontcamp.homework.dto.request.AuthSignUpDto;
import com.sontcamp.homework.dto.type.EProvider;
import com.sontcamp.homework.dto.type.ERole;
import com.sontcamp.homework.dto.type.factory.EProviderFactory;
import com.sontcamp.homework.dto.type.factory.ERoleFactory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "serial_id", nullable = false, unique = true)
    private String serialId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private EProvider provider;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "nickname", length = 12)
    private String nickname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_login", columnDefinition = "TINYINT(1)")
    private Boolean isLogin;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Diary> diaryList  = new ArrayList<>();

    @Builder
    public User(String serialId, String password, ERole role, EProvider provider) {
        this.serialId = serialId;
        this.password = password;
        this.provider = provider;
        this.role = role;
        this.createdAt = LocalDate.now();
        this.isLogin = true;
        this.refreshToken = null;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateInfo(String nickname, String phoneNumber) {
        if(nickname != null && (!Objects.equals(this.nickname, nickname))) {
            this.nickname = nickname;
        }
        if(phoneNumber != null && (!Objects.equals(this.phoneNumber, phoneNumber))) {
            this.phoneNumber = phoneNumber;
        }
    }

    public static User authSignUp(AuthSignUpDto authSignUpDto, String encodedPassword, Boolean isOauth) {
        User user = User.builder()
                .serialId(authSignUpDto.serialId())
                .password(encodedPassword)
                .provider(EProvider.DEFAULT)
                .role(ERole.GUEST)
                .build();

        user.registerComplete(authSignUpDto.nickname(), authSignUpDto.phoneNumber());
        return user;
    }

    public void registerComplete(String nickname, String phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = ERole.USER;
    }
    public void logoutUser() {
        this.isLogin = false;
        this.refreshToken = null;
    }

    public static User fromSignUpJson(AuthSignUpDto authSignUpDto, String password) {
        User user = new User(authSignUpDto.serialId(), password, ERoleFactory.of("GUEST"), EProviderFactory.of("DEFAULT"));
        User user1 = authSignUp(authSignUpDto, password, false);
        user.registerComplete(authSignUpDto.nickname(), authSignUpDto.phoneNumber());
        return user1;
    }
}
