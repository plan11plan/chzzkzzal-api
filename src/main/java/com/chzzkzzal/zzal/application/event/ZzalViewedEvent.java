package com.chzzkzzal.zzal.application.event;

import com.chzzkzzal.zzal.application.dto.ClientInfo;

public record ZzalViewedEvent(
	Long zzalId,
	ClientInfo clientInfo,
	Long memberId

) {
}
