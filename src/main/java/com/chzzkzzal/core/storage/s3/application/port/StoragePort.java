package com.chzzkzzal.core.storage.s3.application.port;

import java.net.URL;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface StoragePort {

	List<String> upload(List<MultipartFile> files);

	String upload(MultipartFile file);

	void delete(String key);

	URL getUrl(String key);
}
