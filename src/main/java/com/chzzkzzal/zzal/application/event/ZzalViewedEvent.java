package com.chzzkzzal.zzal.application.event;

import com.chzzkzzal.zzal.application.port.in.query.ClientInfo;

public record ZzalViewedEvent(
	Long zzalId,
	ClientInfo clientInfo,
	Long memberId
) {
}
