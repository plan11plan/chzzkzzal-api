package com.chzzkzzal.core.auth.domain.dto;

public record TokenResult(
	String refreshToken,
	String externalId
) {
}
