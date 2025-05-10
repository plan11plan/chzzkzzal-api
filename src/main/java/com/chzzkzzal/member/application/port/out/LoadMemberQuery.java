package com.chzzkzzal.member.application.port.out;

import com.chzzkzzal.common.annotation.PublicApi;
import com.chzzkzzal.member.application.query.MemberInfo;
import com.chzzkzzal.member.domain.Member;

@PublicApi("External use by zzal, auth, etc.")
public interface LoadMemberQuery {
	MemberInfo loadById(Long memberId);

	MemberInfo loadByChannelId(String channelId);

	Member loadEntityById(Long memberId);
}
