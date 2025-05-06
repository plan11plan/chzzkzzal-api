package com.chzzkzzal.core.auth.application.impl;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.usecase.LogoutUseCase;
import com.chzzkzzal.core.auth.domain.service.RefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtLogoutUseCase implements LogoutUseCase {
	private final RefreshTokenService refreshTokenService;

	@Override
	public void execute(final HttpServletRequest request, final HttpServletResponse response) {
		refreshTokenService.expireRefreshToken(request, response);
		log.info("User {} logged out", maskId(request.getRemoteAddr()));
	}

	private String maskId(String id) {
		if (id == null || id.length() < 4)
			return "****";
		return id.substring(0, 4) + "****";
	}
}
