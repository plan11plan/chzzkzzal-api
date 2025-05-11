package com.chzzkzzal.zzal.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {
	String upload(MultipartFile file);

	String getUrl(String fileName);
}
