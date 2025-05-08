package com.chzzkzzal.core.storage.s3.adapter.in;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.storage.s3.application.service.StorageFacadeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Facade {

	private final StorageFacadeService service;

	public List<String> uploadFiles(List<MultipartFile> files) {
		return service.uploadFiles(files);
	}

	public String uploadFile(MultipartFile file) {
		return service.uploadFile(file);
	}

	public void deleteFile(String key) {
		service.deleteFile(key);
	}

	public String getFileUrl(String key) {
		return service.getFileUrl(key);
	}
}
