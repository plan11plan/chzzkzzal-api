package com.chzzkzzal.zzal.application.port.in.command;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.zzal.metadata.MediaMeta;

public record SaveZzalCommand(
	String channelId,
	Member member,
	MediaMeta mediaMeta,
	String title,
	String fileUrl
) {
}
