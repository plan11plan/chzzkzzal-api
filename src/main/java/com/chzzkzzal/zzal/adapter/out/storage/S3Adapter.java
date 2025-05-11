package com.chzzkzzal.zzal.adapter.out.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.storage.s3.adapter.in.S3Facade;
import com.chzzkzzal.zzal.application.port.out.FileStoragePort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Adapter implements FileStoragePort {
	private final S3Facade s3;

	public String upload(MultipartFile f) {
		return s3.uploadFile(f);
	}

	public String getUrl(String url) {
		return s3.getFileUrl(url);
	}
}
