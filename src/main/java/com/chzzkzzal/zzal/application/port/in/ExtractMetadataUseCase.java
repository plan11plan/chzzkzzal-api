package com.chzzkzzal.zzal.application.port.in;

import com.chzzkzzal.zzal.application.port.in.query.ExtractMetadataQuery;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;

public interface ExtractMetadataUseCase {

	MediaMeta execute(ExtractMetadataQuery query);
}
