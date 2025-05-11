package com.chzzkzzal.zzal.application.port.in;

import com.chzzkzzal.zzal.application.port.in.query.GetZzalDetailQuery;
import com.chzzkzzal.zzal.application.result.ZzalDetailResponse;

public interface ZzalDetailUseCase {

	ZzalDetailResponse execute(GetZzalDetailQuery query);

}

