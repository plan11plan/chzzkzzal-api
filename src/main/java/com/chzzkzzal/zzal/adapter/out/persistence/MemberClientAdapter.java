package com.chzzkzzal.zzal.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.chzzkzzal.member.application.port.out.LoadMemberQuery;
import com.chzzkzzal.member.application.query.MemberInfo;
import com.chzzkzzal.zzal.application.port.out.LoadMemberPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberClientAdapter implements LoadMemberPort {
	private final LoadMemberQuery memberQuery;

	@Override
	public MemberInfo loadMember(Long memberId) {
		return memberQuery.loadById(memberId);
	}
}
