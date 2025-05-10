package com.chzzkzzal.zzal.application.port.out;

import com.chzzkzzal.member.application.query.MemberInfo;

public interface LoadMemberPort {
	MemberInfo loadMember(Long memberId);
}
