package com.chzzkzzal.member.application.port.out;

import com.chzzkzzal.common.annotation.PublicApi;
import com.chzzkzzal.member.application.query.MemberInfo;

@PublicApi("External use by zzal, auth, etc.")
public interface LoadMemberQuery {
	MemberInfo loadById(Long memberId);
}
