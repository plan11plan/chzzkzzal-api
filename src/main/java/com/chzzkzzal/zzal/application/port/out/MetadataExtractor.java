package com.chzzkzzal.zzal.application.port.out;

import com.chzzkzzal.zzal.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.zzal.ZzalType;

public interface MetadataExtractor<T extends MediaMeta> {
	boolean supports(ZzalType type);

	T extract(final byte[] bytes, final String originalName, final String contentType);

}
