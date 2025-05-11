package com.chzzkzzal.zzal.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.application.query.MemberInfo;
import com.chzzkzzal.zzal.adapter.in.event.Events;
import com.chzzkzzal.zzal.application.event.ZzalViewedEvent;
import com.chzzkzzal.zzal.application.port.in.GetZzalDetailUseCase;
import com.chzzkzzal.zzal.application.port.in.query.GetZzalDetailQuery;
import com.chzzkzzal.zzal.application.port.in.query.result.ZzalDetailResponse;
import com.chzzkzzal.zzal.application.port.out.LoadMemberPort;
import com.chzzkzzal.zzal.application.port.out.LoadZzalPort;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.exception.zzal.ZzalNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetZzalDetailService implements GetZzalDetailUseCase {

	private final LoadMemberPort memberLoader;
	private final LoadZzalPort loadZzalPort;

	public ZzalDetailResponse execute(GetZzalDetailQuery query) {

		Zzal zzal = loadZzalPort.findById(query.zzalId()).orElseThrow(() -> new ZzalNotFoundException());
		Long uploaderId = zzal.getMember().getId();
		MemberInfo member = memberLoader.loadMember(uploaderId);

		Events.raise(
			new ZzalViewedEvent(
				query.zzalId(),
				query.clientInfo(),
				member.id()
			)
		);
		return ZzalDetailResponse.toResponse(zzal, member);
	}

}
