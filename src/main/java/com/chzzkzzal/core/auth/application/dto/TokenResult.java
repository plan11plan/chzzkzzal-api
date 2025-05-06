package com.chzzkzzal.core.auth.application.dto;

public record TokenResult(
	String refreshToken,
	String externalId
) {
}
