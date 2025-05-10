package com.chzzkzzal.zzal.application.port.in;

import java.util.List;

import com.chzzkzzal.zzal.application.result.ZzalDetailResponse;

public interface ZzalGetAllUseCase {
	List<ZzalDetailResponse> getAll();
}
