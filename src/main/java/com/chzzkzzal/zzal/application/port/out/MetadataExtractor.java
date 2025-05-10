package com.chzzkzzal.zzal.application.port.out;

import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.ZzalType;

public interface MetadataExtractor<T extends MediaMeta> {
	boolean supports(ZzalType type);

	T extract(MultipartFile multipartFile);

}
