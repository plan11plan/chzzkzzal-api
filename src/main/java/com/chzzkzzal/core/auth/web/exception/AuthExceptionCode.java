package com.chzzkzzal.core.auth.web.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.chzzkzzal.core.common.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptionCode implements ErrorCode {
	ALREADY_REGISTERED_USER(BAD_REQUEST, "AUTH_100", "다른 소셜 계정으로 이미 가입된 사용자입니다."),
	AUTHENTICATION_REQUIRED(UNAUTHORIZED, "AUTH_200", "인증 정보가 유효하지 않습니다."),
	REFRESH_TOKEN_INVALID(UNAUTHORIZED, "AUTH_300", "리프레시 토큰이 유효하지 않습니다."),
	;

	private final HttpStatus status;
	private final String code;
	private final String message;
}
