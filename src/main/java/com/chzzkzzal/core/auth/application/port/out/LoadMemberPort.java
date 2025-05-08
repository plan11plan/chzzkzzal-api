package com.chzzkzzal.core.auth.application.port.out;

import java.util.Optional;

import com.chzzkzzal.member.domain.Member;

public interface LoadMemberPort {

	Optional<Member> findByChannelId(String channelId);
}
