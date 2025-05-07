package com.chzzkzzal.member.adapter.out;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.domain.repository.SaveMemberPort;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveMemberAdapter implements SaveMemberPort {
	private final MemberRepository memberRepository;

	@Override
	public Member save(final String externalId, final String channelName) {
		Member member = new Member(channelName, channelName);
		return memberRepository.save(member);
	}
}
