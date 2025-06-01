package com.chzzkzzal.test;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.member.business.application.query.MemberInfo;
import com.chzzkzzal.zzal.business.application.port.in.command.AddHitCommand;
import com.chzzkzzal.zzal.business.application.port.in.query.GetZzalDetailQuery;
import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;
import com.chzzkzzal.zzal.business.application.port.out.LoadMemberPort;
import com.chzzkzzal.zzal.business.application.port.out.LoadZzalPort;
import com.chzzkzzal.zzal.business.application.port.out.MemberHistoryPort;
import com.chzzkzzal.zzal.business.application.port.out.UniqueKeyGenerator;
import com.chzzkzzal.zzal.business.application.port.out.ViewLogPort;
import com.chzzkzzal.zzal.business.domain.hit.ZzalHitCommandService;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;
import com.chzzkzzal.zzal.exception.zzal.ZzalNotFoundException;
import com.chzzkzzal.zzal_view_log.ZzalViewLogDto;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Timed(value = "zzal.detail.transaction", description = "짤 상세 조회 하나의 트랜잭션")
public class TransactionGetZzalDetailService {

	private final LoadMemberPort memberLoader;
	private final LoadZzalPort loadZzalPort;
	private final ViewLogPort viewLogPort;
	private final MemberHistoryPort historyPort;
	private final UniqueKeyGenerator keyGen;
	private final ZzalHitCommandService zzalHitCommandService;

	@Transactional
	public ZzalDetailResponse execute(GetZzalDetailQuery query) {

		Zzal zzal = loadZzalPort.findById(query.zzalId()).orElseThrow(() -> new ZzalNotFoundException());
		Long uploaderId = zzal.getMember().getId();
		MemberInfo member = memberLoader.loadMember(uploaderId);

		String uniqueKey = keyGen.generate(
			query.clientInfo().ipAddress(),
			query.clientInfo().userAgent()
		);
		zzalHitCommandService.addHit(new AddHitCommand(zzal.getId(), query.clientInfo()));

		viewLogPort.save(new ZzalViewLogDto(
			zzal.getId(), member.id(), uniqueKey,
			query.clientInfo().ipAddress(),
			query.clientInfo().userAgent(),
			query.clientInfo().browserType(),
			query.clientInfo().deviceType(),
			LocalDateTime.now()
		));
		historyPort.addMemberHistory(query.zzalId(), member.id());
		return ZzalDetailResponse.toResponse(zzal, member);
	}

}
