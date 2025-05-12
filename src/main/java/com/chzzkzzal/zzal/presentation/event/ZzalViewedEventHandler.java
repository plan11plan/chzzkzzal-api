package com.chzzkzzal.zzal.presentation.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.application.event.ZzalViewedEvent;
import com.chzzkzzal.zzal.application.port.in.RecordZzalViewUseCase;
import com.chzzkzzal.zzal.application.port.in.command.RecordZzalViewCommand;
import com.chzzkzzal.zzal.domain.zzal.ViewAddable;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalViewedEventHandler {
	private final RecordZzalViewUseCase recordView;

	@EventListener
	public void handle(ZzalViewedEvent e) {

		boolean countable = e.zzal() instanceof ViewAddable;

		recordView.record(new RecordZzalViewCommand(
			e.zzalId(),
			e.memberId(),
			e.clientInfo(),
			countable
		));
	}
}
