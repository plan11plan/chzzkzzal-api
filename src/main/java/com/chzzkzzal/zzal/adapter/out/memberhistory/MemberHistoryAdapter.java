package com.chzzkzzal.zzal.adapter.out.memberhistory;

import org.springframework.stereotype.Component;

import com.chzzkzzal.myviewhistory.MyViewHistoryService;
import com.chzzkzzal.zzal.application.port.out.MemberHistoryPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberHistoryAdapter implements MemberHistoryPort {

	private final MyViewHistoryService myViewHistoryService;

	@Override
	public void addMemberHistory(final Long zzalId, final Long memberId) {
		myViewHistoryService.addMemberViewHistory(zzalId, memberId);
	}
}
