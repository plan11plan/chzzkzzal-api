package com.chzzkzzal.zzal.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.application.port.in.ZzalGetAllUseCase;
import com.chzzkzzal.zzal.application.port.out.LoadZzalPort;
import com.chzzkzzal.zzal.application.result.ZzalDetailResponse;
import com.chzzkzzal.zzal.domain.zzal.Zzal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalGetAllUseCaseImpl implements ZzalGetAllUseCase {
	private final LoadZzalPort loadZzalPort;

	public List<ZzalDetailResponse> getAll() {
		List<Zzal> zzals = loadZzalPort.findAll();
		List<ZzalDetailResponse> collect = zzals.stream()
			.map(zzal -> ZzalDetailResponse.toResponse(zzal, zzal.getMember()))
			.collect(Collectors.toList());
		return collect;
	}
}
