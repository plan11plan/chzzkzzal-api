package com.chzzkzzal.zzal.adapter.out.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.application.port.out.FileStoragePort;

@Service
public class S3Port implements FileStoragePort {
	@Override
	public String storeFile(MultipartFile file) {
		return null;
	}

	@Override
	public String loadFile(String fileName) {
		return null;
	}
}
