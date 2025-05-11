package com.chzzkzzal.zzal.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.zzal.application.port.in.AddHitUsecase;
import com.chzzkzzal.zzal.application.port.in.RecordZzalViewUseCase;
import com.chzzkzzal.zzal.application.port.in.command.AddHitCommand;
import com.chzzkzzal.zzal.application.port.in.command.RecordZzalViewCommand;
import com.chzzkzzal.zzal.application.port.out.MemberHistoryPort;
import com.chzzkzzal.zzal.application.port.out.UniqueKeyGenerator;
import com.chzzkzzal.zzal.application.port.out.ViewLogPort;
import com.chzzkzzal.zzal_view_log.ZzalViewLogDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordZzalViewService implements RecordZzalViewUseCase {

	private final AddHitUsecase addHit;          // ✅ 기존 Port 재사용
	private final ViewLogPort viewLogPort;
	private final MemberHistoryPort historyPort;
	private final UniqueKeyGenerator keyGen;

	@Override
	public void record(RecordZzalViewCommand c) {

		// 1) 조회수 카운트 (ViewAddable 일 때만)
		String uniqueKey = c.countable()
			? addHit.addHit(new AddHitCommand(c.zzalId(), c.clientInfo()))
			: keyGen.generate(c.clientInfo().ipAddress(),
			c.clientInfo().userAgent());

		// 2) 조회 로그 저장
		viewLogPort.save(new ZzalViewLogDto(
			c.zzalId(),
			c.memberId(),
			uniqueKey,
			c.clientInfo().ipAddress(),
			c.clientInfo().userAgent(),
			c.clientInfo().browserType(),
			c.clientInfo().deviceType(),
			LocalDateTime.now()));

		// 3) 개인 시청 이력 (로그인 시)
		if (c.memberId() != null) {
			historyPort.addMemberHistory(c.zzalId(), c.memberId());
		}
	}
}
