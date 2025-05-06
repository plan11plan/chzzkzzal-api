package com.chzzkzzal.core.security.infrastructure.jwt;

import static com.chzzkzzal.core.security.domain.TokenName.*;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.common.properties.SecurityProperties;
import com.chzzkzzal.core.common.properties.TokenProperties;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenInjector {
	private final TokenProperties tokenProperties;
	private final SecurityProperties securityProperties;

	public void injectRefreshTokenToCookie(TokenResult result, HttpServletResponse response) {
		addCookie(REFRESH_TOKEN.name(), result.refreshToken(),
			(int)tokenProperties.expirationTime().refreshTokenHours(),
			response
		);
	}

	public void addCookie(
		String name,
		String value,
		int maxAge,
		HttpServletResponse response
	) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		cookie.setHttpOnly(securityProperties.cookie().httpOnly());
		cookie.setDomain(securityProperties.cookie().domain());
		cookie.setSecure(securityProperties.cookie().secure());
		cookie.setAttribute("SameSite", "None");
		response.addCookie(cookie);
	}

	public void invalidateCookie(
		String name,
		HttpServletResponse response
	) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		cookie.setHttpOnly(securityProperties.cookie().httpOnly());
		cookie.setDomain(securityProperties.cookie().domain());
		cookie.setSecure(securityProperties.cookie().secure());
		cookie.setAttribute("SameSite", "None");
		response.addCookie(cookie);
	}
}
