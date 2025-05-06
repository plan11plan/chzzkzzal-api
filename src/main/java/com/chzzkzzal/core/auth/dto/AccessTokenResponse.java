package com.chzzkzzal.core.auth.dto;

import com.chzzkzzal.core.common.properties.TokenProperties;

import lombok.Builder;

@Builder
public record AccessTokenResponse(
	String accessToken,
	String expirationTime
) {
	public static AccessTokenResponse of(String accessToken, TokenProperties tokenProperties) {
		return AccessTokenResponse.builder()
			.accessToken(accessToken)
			.expirationTime(String.valueOf(tokenProperties.expirationTime().accessToken()))
			.build();
	}
}
