package com.chzzkzzal.zzal.application.port.in;

import com.chzzkzzal.zzal.application.result.ZzalDetailResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface ZzalDetailUseCase {

	ZzalDetailResponse getZZal(Long zzalId, HttpServletRequest request);
}

