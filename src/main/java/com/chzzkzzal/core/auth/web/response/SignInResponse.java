package com.chzzkzzal.core.auth.web.response;

public record SignInResponse(
	String channelName,
	String accessToken,
	String refreshToken
) {
}
