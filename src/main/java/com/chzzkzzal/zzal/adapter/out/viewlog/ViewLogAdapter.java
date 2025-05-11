package com.chzzkzzal.zzal.adapter.out.viewlog;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.application.port.out.ViewLogPort;
import com.chzzkzzal.zzal_view_log.ZzalViewLogDto;
import com.chzzkzzal.zzal_view_log.ZzalViewLogService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ViewLogAdapter implements ViewLogPort {
	private final ZzalViewLogService service;

	@Override
	public void save(final ZzalViewLogDto zzalViewLogDto) {
		service.addViewLog(zzalViewLogDto);
	}
}
