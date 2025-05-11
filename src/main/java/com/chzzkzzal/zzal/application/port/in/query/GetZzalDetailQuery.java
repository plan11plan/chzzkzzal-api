package com.chzzkzzal.zzal.application.port.in.query;

public record GetZzalDetailQuery(
	Long zzalId,
	ClientInfo clientInfo
) {
}
