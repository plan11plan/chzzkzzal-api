package com.chzzkzzal.zzal.adapter.out.metadata.image;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class GifFrameAnalyzer {
	public int countFrames(ImageReader reader) throws IOException {
		return reader.getNumImages();
	}
}
