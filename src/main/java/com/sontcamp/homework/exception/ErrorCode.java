package com.sontcamp.homework.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND_RESOURCE(40400, HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    NOT_FOUND_USER(40401, HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    NOT_END_POINT(40402, HttpStatus.NOT_FOUND, "존재하지 않는 엔드포인트입니다."),

    ACCESS_DENIED(40301, HttpStatus.FORBIDDEN, "접근에 실패하였습니다."),

    BAD_REQUEST(40000, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_ROLE(40001, HttpStatus.BAD_REQUEST, "유효하지 않은 권한입니다."),
    INVALID_PROVIDER(40002, HttpStatus.BAD_REQUEST, "유효하지 않은 제공자입니다."),
    METHOD_NOT_ALLOWED(40003, HttpStatus.BAD_REQUEST, "지원하지 않는 HTTP Method 입니다."),
    UNSUPPORTED_MEDIA_TYPE(40004, HttpStatus.BAD_REQUEST, "지원하지 않는 미디어 타입입니다."),
    INVALID_ARGUMENT(40005, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자입니다."),
    MISSING_REQUEST_PARAMETER(40006, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(40007, HttpStatus.BAD_REQUEST, "요청 파라미터의 형태가 잘못되었습니다."),
    MISSING_REQUEST_COOKIE(40008, HttpStatus.BAD_REQUEST, "필수 요청 쿠키가 누락되었습니다."),
    DUPLICATED_SERIAL_ID(40009, HttpStatus.BAD_REQUEST, "중복된 ID 입니다."),
    INVALID_PARAMETER(40010, HttpStatus.BAD_REQUEST, "유효하지 않은 파라미터입니다."),
    NOT_FOUND_PROVIDER(40011, HttpStatus.BAD_REQUEST, "존재하지 않는 제공자입니다."),
    FAILURE_LOGIN(40012, HttpStatus.BAD_REQUEST, "로그인에 실패하였습니다."),
    TOKEN_MALFORMED_ERROR(40013, HttpStatus.BAD_REQUEST, "형식에 맞지 않는 토큰입니다."),
    TOKEN_TYPE_ERROR(40014, HttpStatus.BAD_REQUEST, "토큰 타입 오류입니다."),
    TOKEN_UNSUPPORTED_ERROR(40015, HttpStatus.BAD_REQUEST, "지원하지 않는 토큰입니다."),
    TOKEN_UNKNOWN_ERROR(40016, HttpStatus.BAD_REQUEST, "알 수 없는 토큰 오류입니다."),

    TOKEN_EXPIRED_ERROR(40101, HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    UNAUTHORIZED(40102, HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    EXTERNAL_API_ERROR(40103, HttpStatus.UNAUTHORIZED, "외부 API 호출에 실패하였습니다."),

    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러입니다."),
    INVALID_AUTHENTICATION_HEADER(50001, HttpStatus.BAD_REQUEST, "유효하지 않은 인증 헤더입니다."),

    ;

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;
}
