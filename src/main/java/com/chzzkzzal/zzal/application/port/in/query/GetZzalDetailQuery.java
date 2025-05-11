package com.chzzkzzal.zzal.application.port.in.query;

import com.chzzkzzal.zzal.application.dto.ClientInfo;

public record GetZzalDetailQuery(
	Long zzalId,
	ClientInfo clientInfo
) {
}
