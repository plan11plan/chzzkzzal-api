package com.chzzkzzal.zzal.application.port.in.command;

import com.chzzkzzal.zzal.application.dto.ClientInfo;

// application/port/in/command/RecordZzalViewCommand.java
public record RecordZzalViewCommand(
	Long zzalId,
	Long memberId,
	ClientInfo clientInfo,
	boolean countable
) {
}
