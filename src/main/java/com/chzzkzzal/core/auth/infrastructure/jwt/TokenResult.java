package com.chzzkzzal.core.auth.infrastructure.jwt;

public record TokenResult(
	String refreshToken,
	String externalId
) {
}
