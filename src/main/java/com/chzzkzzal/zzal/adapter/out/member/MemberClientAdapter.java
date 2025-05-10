package com.chzzkzzal.zzal.adapter.out.member;

import org.springframework.stereotype.Component;

import com.chzzkzzal.member.application.port.out.LoadMemberQuery;
import com.chzzkzzal.member.application.query.MemberInfo;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.application.port.out.LoadMemberPort;

import lombok.RequiredArgsConstructor;

@Component("ZzalMemberClientAdapter")
@RequiredArgsConstructor
public class MemberClientAdapter implements LoadMemberPort {
	private final LoadMemberQuery memberQuery;

	@Override
	public MemberInfo loadMember(Long memberId) {
		return memberQuery.loadById(memberId);
	}

	@Override
	public Member loadMemberEntity(final Long memberId) {
		return memberQuery.loadEntityById(memberId);
	}
}
