package com.chzzkzzal.core.auth.application.impl;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.usecase.CheckLoginStatusUseCase;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenResolver;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenValidator;
import com.chzzkzzal.core.auth.web.response.LoginCheckResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckLoginStatusService implements CheckLoginStatusUseCase {
	private final TokenResolver tokenResolver;
	private final TokenValidator tokenValidator;

	@Override
	public LoginCheckResponse execute(final HttpServletRequest request) {
		String jwtToken = tokenResolver
			.resolveFromCookie(request, SESSION.name())
			.orElseThrow(IllegalAccessError::new);

		boolean isAuthenticated = tokenValidator.validateToken(jwtToken);
		log.info("로그인 상태 : {}", isAuthenticated);

		return new LoginCheckResponse(isAuthenticated);
	}
	
}

