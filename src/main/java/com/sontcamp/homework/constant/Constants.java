package com.sontcamp.homework.constant;

import java.util.List;

public class Constants {
    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String USER_ROLE_CLAIM_NAME = "rol";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final List<String> NO_NEED_AUTH_URLS = List.of(
            "/auth/sign-in",
            "/auth/kakao",
            "/oauth2/authorization/kakao",
            "/login/oauth2/code/kakao"
    );

    public static final List<String> ALL_URLS = List.of(
            "/auth/reissue",
            "/auth/sign-out"
    );

    public static final  List<String> ADMIN_URLS = List.of(
            "/admin/**"
    );

    public static final  List<String> USER_URLS = List.of(
            "/**"
    );

    public static final  List<String> GUEST_URLS = List.of(
            "/oauth2/sign-up"
    );
}