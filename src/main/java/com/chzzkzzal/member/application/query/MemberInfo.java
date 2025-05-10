package com.chzzkzzal.member.application.query;

import com.chzzkzzal.common.annotation.PublicApi;

@PublicApi
public record MemberInfo(
	Long id,
	String channelName,
	String nickname
) {
}
