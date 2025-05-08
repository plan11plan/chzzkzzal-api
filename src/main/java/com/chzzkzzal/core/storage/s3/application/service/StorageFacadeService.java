package com.chzzkzzal.core.storage.s3.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.storage.s3.application.port.StoragePort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageFacadeService {

	private final StoragePort storage;

	public List<String> uploadFiles(List<MultipartFile> files) {
		return storage.upload(files);
	}

	public String uploadFile(MultipartFile file) {
		return storage.upload(file);
	}

	public void deleteFile(String key) {
		storage.delete(key);
	}

	public String getFileUrl(String key) {
		return storage.getUrl(key).toString();
	}
}
