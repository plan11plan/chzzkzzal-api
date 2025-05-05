package com.chzzkzzal.core.security.service;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.security.infrastructure.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccessTokenService {
	private final TokenProvider tokenProvider;

	public String issueAccessToken(String memberId) {
		String accessToken = tokenProvider.createAccessToken(memberId);
		return accessToken;
	}

}
