package com.chzzkzzal.core.auth.infrastructure.jwt;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 역할: HTTP 요청으로부터 AccessToken, RefreshToken 추출 (Header + Cookie)
 * 부가 기능: JWT에서 subject 추출 (토큰 파싱)
 */
@Component
public class TokenResolver {

	public String extractCookie(HttpServletRequest request) {
		// 1. 요청에서 쿠키 추출
		Cookie[] cookies = request.getCookies();
		String jwtToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (SESSION.name().equals(cookie.getName())) {
					jwtToken = cookie.getValue();
					break;
				}
			}
		}
		return jwtToken;
	}
}
