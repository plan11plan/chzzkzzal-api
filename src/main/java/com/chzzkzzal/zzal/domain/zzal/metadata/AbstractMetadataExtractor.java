package com.chzzkzzal.zzal.domain.zzal.metadata;

import com.chzzkzzal.zzal.application.port.out.MetadataExtractor;

public abstract class AbstractMetadataExtractor<T extends MediaMeta>
	implements MetadataExtractor<T> {

	@Override
	public final T extract(final byte[] bytes, final String originalName, final String contentType) {

		return parseMetadata(bytes, originalName, contentType);
	}

	protected abstract T parseMetadata(byte[] bytes, final String originalName, final String contentType);
}
