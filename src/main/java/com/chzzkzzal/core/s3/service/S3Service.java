package com.chzzkzzal.core.s3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.chzzkzzal.core.s3.service.S3Deleter;
import com.chzzkzzal.core.s3.service.S3MetadataProvider;
import com.chzzkzzal.core.s3.service.S3Uploader;
import com.chzzkzzal.core.s3.service.S3UrlProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

	private final S3Uploader s3Uploader;
	private final S3Deleter s3Deleter;
	private final S3UrlProvider s3UrlProvider;

	public List<String> uploadFiles(List<MultipartFile> multipartFiles) {
		List<String> fileNameList = new ArrayList<>();
		multipartFiles.forEach(file -> {
			String fileName = s3Uploader.upload(file);
			fileNameList.add(fileName);
		});
		return fileNameList;
	}

	public void deleteFile(String fileName) {
		s3Deleter.delete(fileName);
	}

	public String getFileUrl(String fileName) {
		return s3UrlProvider.getUrl(fileName);
	}

}
