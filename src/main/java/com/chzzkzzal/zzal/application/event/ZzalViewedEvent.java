package com.chzzkzzal.zzal.application.event;

import jakarta.servlet.http.HttpServletRequest;

public record ZzalViewedEvent(
	Long zzalId,
	HttpServletRequest httpServletRequest,
	Long memberId
) {
}
