package com.chzzkzzal.core.auth.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.application.port.out.LoadMemberPort;
import com.chzzkzzal.core.auth.application.port.out.SaveMemberPort;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberJpaAdapter implements SaveMemberPort, LoadMemberPort {
	private final MemberRepository memberRepository;

	@Override
	public Optional<Member> findByChannelId(final String channelId) {
		return memberRepository.findByChannelId(channelId);
	}

	@Override
	public Member save(final String externalId, final String channelName) {
		Member member = new Member(channelName, externalId);
		return memberRepository.save(member);
	}
}
