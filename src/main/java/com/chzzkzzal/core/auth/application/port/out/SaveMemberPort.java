package com.chzzkzzal.core.auth.application.port.out;

import com.chzzkzzal.member.domain.Member;

public interface SaveMemberPort {
	Member save(final String externalId, final String channelName);
}
