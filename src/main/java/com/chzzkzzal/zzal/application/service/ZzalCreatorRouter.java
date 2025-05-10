package com.chzzkzzal.zzal.application.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.factory.ZzalCreator;
import com.chzzkzzal.zzal.exception.zzal.ZzalNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZzalCreatorRouter {
	private final List<ZzalCreator> factories;

	public ZzalCreator getFactory(MediaMeta metadata) {
		return factories.stream()
			.filter(f -> f.supports(metadata))
			.findFirst()
			.orElseThrow(ZzalNotFoundException::new);
	}
}
