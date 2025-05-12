package com.chzzkzzal.zzal.application.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.application.port.out.MetadataExtractor;
import com.chzzkzzal.zzal.application.util.MultipartFileContentType;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.ZzalType;
import com.chzzkzzal.zzal.exception.metadata.MetadataContentTypeNullException;
import com.chzzkzzal.zzal.exception.metadata.MetadataUnsupportedFormatException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MetadataProvider {

	private final List<MetadataExtractor<? extends MediaMeta>> extractors;

	public MediaMeta getMetadata(byte[] bytes, String originalName, String contentType) {
		if (contentType == null)
			throw new MetadataContentTypeNullException();

		MultipartFileContentType mct = MultipartFileContentType.fromString(contentType);
		ZzalType type = mct.toZzalType();

		return extractors.stream()
			.filter(e -> e.supports(type))
			.findFirst()
			.orElseThrow(MetadataUnsupportedFormatException::new)
			.extract(bytes, originalName, contentType);
	}
}
