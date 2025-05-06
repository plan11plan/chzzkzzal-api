package com.chzzkzzal.core.auth.application.impl;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.usecase.CheckLoginStatusUseCase;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenProvider;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenResolver;
import com.chzzkzzal.core.auth.web.response.LoginCheckResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckLoginStatusService implements CheckLoginStatusUseCase {
	private final TokenProvider tokenProvider;
	private final TokenResolver tokenResolver;

	@Override
	public LoginCheckResponse execute(final HttpServletRequest request) {
		String jwtToken = tokenResolver
			.resolveFromCookie(request, SESSION.name())
			.orElseThrow(IllegalAccessError::new);

		return checkLoginStatus(jwtToken);
	}

	private LoginCheckResponse checkLoginStatus(final String jwtToken) {
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

