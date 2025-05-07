package com.chzzkzzal.core.auth.infrastructure.jwt;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.chzzkzzal.common.properties.SecurityProperties;
import com.chzzkzzal.common.properties.TokenProperties;
import com.chzzkzzal.core.auth.domain.dto.TokenResult;

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

	public void addCookie(String name, String value, int maxAge, HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
			.path("/")
			.maxAge(Duration.ofSeconds(maxAge))
			.httpOnly(securityProperties.cookie().httpOnly())
			.domain(securityProperties.cookie().domain())
			.secure(securityProperties.cookie().secure())
			.sameSite("none")
			.build();
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	}

	//	public HttpServletResponse addCookie(String name, String value, int maxAge, HttpServletResponse response) {
	//		ResponseCookie cookie = ResponseCookie.from(name, value)
	//			.path("/")
	//			.maxAge(Duration.ofSeconds(maxAge))
	//			.httpOnly(securityProperties.cookie().httpOnly())
	//			.domain(securityProperties.cookie().domain())
	//			.secure(securityProperties.cookie().secure())
	//			.sameSite("none")
	//			.build();
	//		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	//		return response;
	//	}

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
