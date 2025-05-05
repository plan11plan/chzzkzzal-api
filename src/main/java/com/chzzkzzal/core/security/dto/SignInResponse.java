package com.chzzkzzal.core.security.dto;

public record SignInResponse(
	String channelName,
	String accessToken,
	String refreshToken
) {
}
