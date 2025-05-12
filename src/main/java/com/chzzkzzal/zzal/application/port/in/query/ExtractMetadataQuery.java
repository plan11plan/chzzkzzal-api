package com.chzzkzzal.zzal.application.port.in.query;

public record ExtractMetadataQuery(
	byte[] bytes,
	String originalFilename,
	String contentType
) {
}
