package com.chzzkzzal.member.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.domain.repository.LoadMemberPort;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoadMemberAdapter implements LoadMemberPort {
	private final MemberRepository memberRepository;

	@Override
	public Optional<Member> findByChannelId(String channelId) {
		return memberRepository.findByChannelId(channelId);
	}
}
