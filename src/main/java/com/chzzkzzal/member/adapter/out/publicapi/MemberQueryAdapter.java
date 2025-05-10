package com.chzzkzzal.member.adapter.out.publicapi;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.application.port.out.LoadMemberQuery;
import com.chzzkzzal.member.application.query.MemberInfo;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberQueryAdapter implements LoadMemberQuery {
	private final MemberRepository repository;

	@Override
	public MemberInfo loadById(Long memberId) {
		Member member = repository.findById(memberId)
			.orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다"));
		return new MemberInfo(member.getId(), member.getChannelId(), member.getChannelName());
	}

	@Override
	public MemberInfo loadByChannelId(String channelId) {
		Member member = repository.findByChannelId(channelId)
			.orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다"));
		return new MemberInfo(member.getId(), member.getChannelId(), member.getChannelName());
	}

	@Override
	public Member loadEntityById(final Long memberId) {
		return repository.findById(memberId)
			.orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다"));
	}

}
