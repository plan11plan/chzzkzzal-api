package com.chzzkzzal.core.auth.application.impl;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.usecase.CheckLoginStatusUseCase;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenProvider;
import com.chzzkzzal.core.auth.web.response.LoginCheckResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckLoginStatusService implements CheckLoginStatusUseCase {
	private final TokenProvider tokenProvider;

	@Override
	public LoginCheckResponse execute(final HttpServletRequest request) {
		// 1. 요청에서 쿠키 추출
		Cookie[] cookies = request.getCookies();
		String jwtToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("SESSION".equals(cookie.getName())) {
					jwtToken = cookie.getValue();
					break;
				}
			}
		}

		// 2. 토큰 검증
		boolean isAuthenticated = false;
		System.out.println(jwtToken);
		if (jwtToken != null) {
			isAuthenticated = tokenProvider.validateToken(jwtToken); // JWT 검증 로직
		}
		System.out.println("로그인 여부 확인");
		System.out.println(isAuthenticated);
		LoginCheckResponse response = new LoginCheckResponse(isAuthenticated);
		return response;
	}
}

