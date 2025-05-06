package com.chzzkzzal.core.auth.dto;

public record SignInResponse(
	String channelName,
	String accessToken,
	String refreshToken
) {
}
