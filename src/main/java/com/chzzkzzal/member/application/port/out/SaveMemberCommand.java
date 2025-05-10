package com.chzzkzzal.member.application.port.out;

import com.chzzkzzal.common.annotation.PublicApi;
import com.chzzkzzal.member.application.query.MemberInfo;

@PublicApi("External use to register member if not exists.")
public interface SaveMemberCommand {
	MemberInfo saveIfNotExist(String externalId, String channelName);

}
