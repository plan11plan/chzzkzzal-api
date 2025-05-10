package com.chzzkzzal.core.auth.application.port.out;

import com.chzzkzzal.member.application.query.MemberInfo;

public interface LoadMemberQueryPort {
	MemberInfo loadById(Long memberId);

	MemberInfo loadByChannelId(String channelId);
}
