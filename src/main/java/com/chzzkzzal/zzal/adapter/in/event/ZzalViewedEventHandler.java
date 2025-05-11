package com.chzzkzzal.zzal.adapter.in.event;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.chzzkzzal.myviewhistory.MyViewHistoryService;
import com.chzzkzzal.zzal.application.dto.ClientInfo;
import com.chzzkzzal.zzal.application.event.ZzalViewedEvent;
import com.chzzkzzal.zzal.application.port.in.AddHitUsecase;
import com.chzzkzzal.zzal.application.port.in.command.AddHitCommand;
import com.chzzkzzal.zzal_view_log.ZzalViewLogDto;
import com.chzzkzzal.zzal_view_log.ZzalViewLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalViewedEventHandler {
	private final AddHitUsecase addHitUsecase;
	private final MyViewHistoryService myViewHistoryService;
	private final ZzalViewLogService zzalViewLogService;

	@EventListener(ZzalViewedEvent.class)
	// @TransactionalEventListener(
	// 	classes = ZzalViewedEvent.class,
	// 	phase = TransactionPhase.AFTER_COMMIT
	// )
	public void handle(ZzalViewedEvent event) {
		Long zzalId = event.zzalId();
		ClientInfo clientInfo = event.clientInfo();
		Long memberId = event.memberId();
		String uniqueIdentifier = addHitUsecase.addHit(new AddHitCommand(zzalId, clientInfo));
		zzalViewLogService.addViewLog(getZzalViewLogDto(memberId, zzalId, clientInfo, uniqueIdentifier));
		if (memberId != null) {
			myViewHistoryService.addMemberViewHistory(zzalId, memberId);
		}
	}

	private static ZzalViewLogDto getZzalViewLogDto(Long memberId, Long zzalId, ClientInfo clientInfo,
		String uniqueIdentifier) {
		return new ZzalViewLogDto(
			zzalId,
			memberId,
			uniqueIdentifier,
			clientInfo.ipAddress(),
			clientInfo.userAgent(),
			clientInfo.browserType(),
			clientInfo.deviceType(),
			LocalDateTime.now()
		);
	}
}
