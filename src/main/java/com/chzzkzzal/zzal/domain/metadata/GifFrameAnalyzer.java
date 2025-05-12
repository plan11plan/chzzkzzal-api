package com.chzzkzzal.zzal.domain.metadata;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class GifFrameAnalyzer {
	public int countFrames(CustomImageReader reader) throws IOException {
		return reader.getNumImages();
	}
}
