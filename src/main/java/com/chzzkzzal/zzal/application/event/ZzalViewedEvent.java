package com.chzzkzzal.zzal.application.event;

import com.chzzkzzal.zzal.application.dto.ClientInfo;
import com.chzzkzzal.zzal.domain.zzal.zzal.entity.Zzal;

public record ZzalViewedEvent(
	Zzal zzal,
	Long zzalId,
	ClientInfo clientInfo,
	Long memberId

) {
}
