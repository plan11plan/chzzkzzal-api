package com.chzzkzzal.core.auth.application.port.out;

import com.chzzkzzal.member.application.query.MemberInfo;

public interface SaveMemberCommandPort {
	MemberInfo saveIfNotExist(final String externalId, final String channelName);
}
