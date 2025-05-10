package com.chzzkzzal.zzal.application.port.out;

import com.chzzkzzal.member.application.query.MemberInfo;
import com.chzzkzzal.member.domain.Member;

public interface LoadMemberPort {
	MemberInfo loadMember(Long memberId);

	Member loadMemberEntity(Long memberId);
}
