package com.chzzkzzal.zzal.business.application.port.in;

import java.util.List;

import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;
import com.chzzkzzal.zzal.util.CursorRequest;
import com.chzzkzzal.zzal.util.CursorResponse;

public interface GetZzalAllUseCase {
	List<ZzalDetailResponse> getAll();

	CursorResponse<ZzalDetailResponse> getAllByCursor(CursorRequest cursorRequest);
}
