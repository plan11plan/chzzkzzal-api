package com.chzzkzzal.core.auth.application.jwt;

public record TokenResult(
	String refreshToken,
	String externalId
) {
}
