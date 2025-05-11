package com.chzzkzzal.zzal.application.port.in.command;

import com.chzzkzzal.zzal.application.port.in.query.ClientInfo;

public record AddHitCommand(
	Long zzalId,
	ClientInfo clientInfo
) {
}
