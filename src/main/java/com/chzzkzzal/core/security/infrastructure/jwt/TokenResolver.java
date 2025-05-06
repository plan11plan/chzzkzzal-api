package com.chzzkzzal.core.security.infrastructure.jwt;

import static com.chzzkzzal.core.security.domain.TokenName.*;
import static org.springframework.http.HttpHeaders.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenResolver {
	private static final String REPLACE_BEARER_PATTERN = "^Bearer( )*";
	private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer .*");

	public Optional<String> resolveRefreshTokenFromRequest(HttpServletRequest request) {
		return resolveFromCookie(request, REFRESH_TOKEN.name());
	}

	private Optional<String> resolveFromCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (Objects.isNull(cookies)) {
			return Optional.empty();
		}

		return Arrays.stream(cookies)
			.filter(cookie -> Objects.equals(cookie.getName(), cookieName))
			.map(Cookie::getValue)
			.findFirst();
	}

	private static Optional<String> resolveFromHeader(HttpServletRequest request) {
		Iterator<String> authorizations = request.getHeaders(AUTHORIZATION).asIterator();

		return Optional.ofNullable(authorizations)
			.filter(Iterator::hasNext)
			.map(Iterator::next)
			.filter(auth -> StringUtils.hasText(auth) && BEARER_PATTERN.matcher(auth).matches())
			.map(auth -> auth.replaceAll(REPLACE_BEARER_PATTERN, ""));
	}
}
