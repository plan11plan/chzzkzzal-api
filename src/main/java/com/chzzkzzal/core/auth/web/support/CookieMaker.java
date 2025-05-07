package com.chzzkzzal.core.auth.web.support;

import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.SecurityProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieMaker {
	private final SecurityProperties securityProperties;

	public ResponseCookie makeCookie(String name, String value, Duration duration) {
		return ResponseCookie.from(name, value)
			.path("/")
			.sameSite(securityProperties.cookie().sameSite())
			.httpOnly(securityProperties.cookie().httpOnly())
			.secure(securityProperties.cookie().secure())
			.domain(securityProperties.cookie().domain())
			.maxAge(duration)
			.build();
	}

}
