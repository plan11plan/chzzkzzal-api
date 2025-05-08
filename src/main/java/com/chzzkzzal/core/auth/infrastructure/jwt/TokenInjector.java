package com.chzzkzzal.core.auth.infrastructure.jwt;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.chzzkzzal.common.properties.SecurityProperties;
import com.chzzkzzal.common.properties.TokenProperties;
import com.chzzkzzal.core.auth.domain.dto.TokenResult;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenInjector {
	private final TokenProperties tokenProperties;
	private final SecurityProperties securityProperties;

	public void injectRefreshTokenToCookie(TokenResult result, HttpServletResponse response) {
		addCookie(
			REFRESH_TOKEN.name(),
			result.refreshToken(),
			Duration.ofHours(tokenProperties.expirationTime().refreshTokenHours()),
			response
		);
	}

	public void invalidateCookie(String name, HttpServletResponse response) {
		addCookie(name, "", Duration.ZERO, response);
	}

	public void addCookie(String name, String value, Duration duration, HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
			.path("/")
			.maxAge(duration)
			.httpOnly(securityProperties.cookie().httpOnly())
			.domain(securityProperties.cookie().domain())
			.secure(securityProperties.cookie().secure())
			.sameSite(securityProperties.cookie().sameSite())
			.build();
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

}
