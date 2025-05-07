package com.chzzkzzal.core.auth.application.usecase.dto;

public record SignInCommand(
	String channelId,
	String channelName
) {
}
