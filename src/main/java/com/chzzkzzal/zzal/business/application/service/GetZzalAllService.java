package com.chzzkzzal.zzal.business.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.business.application.port.in.GetZzalAllUseCase;
import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;
import com.chzzkzzal.zzal.business.application.port.out.LoadZzalPort;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;
import com.chzzkzzal.zzal.util.CursorRequest;
import com.chzzkzzal.zzal.util.CursorResponse;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetZzalAllService implements GetZzalAllUseCase {
	private final LoadZzalPort loadZzalPort;

	@Timed(value = "zzal.all", description = "짤 메인 페이지 전체 조회")
	@Override
	public List<ZzalDetailResponse> getAll() {
		List<Zzal> zzals = loadZzalPort.findAll();

		return zzals.stream()
			.map(zzal -> ZzalDetailResponse.toResponse(zzal, zzal.getMember()))
			.collect(Collectors.toList());
	}

	@Timed(value = "zzal.all.cursor", description = "짤 메인 페이지 커서 조회")
	@Override
	public CursorResponse<ZzalDetailResponse> getAllByCursor(CursorRequest cursorRequest) {
		Pageable pageable = PageRequest.of(0, cursorRequest.size(), Sort.by(Sort.Direction.DESC, "id"));

		List<Zzal> zzals;
		if (cursorRequest.hasKey()) {
			zzals = loadZzalPort.findByIdLessThan(cursorRequest.key(), pageable);
		} else {
			zzals = loadZzalPort.findAll(pageable).stream().toList();
		}

		// nextKey 계산 -> 가져온 짤Id (최소값)
		long nextKey = zzals.stream()
			.mapToLong(Zzal::getId)
			.min()
			.orElse(CursorRequest.NONE_KEY);
		List<ZzalDetailResponse> responses = zzals.stream()
			.map(zzal -> ZzalDetailResponse.toResponse(zzal, zzal.getMember()))
			.collect(Collectors.toList());

		return new CursorResponse<>(
			cursorRequest.next(nextKey),
			responses
		);
	}

}
