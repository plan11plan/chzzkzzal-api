package com.chzzkzzal.member.adapter.out.publicapi;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.application.port.out.SaveMemberCommand;
import com.chzzkzzal.member.application.query.MemberInfo;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCommandAdapter implements SaveMemberCommand {
	private final MemberRepository repository;

	@Override
	public MemberInfo saveIfNotExist(String externalId, String channelName) {
		Member member = repository.findByChannelId(externalId)
			.orElseGet(() -> repository.save(new Member(channelName, externalId)));

		return new MemberInfo(member.getId(), member.getChannelId(), member.getChannelName());

	}
}
