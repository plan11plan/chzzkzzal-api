package com.chzzkzzal.zzal.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {
	String storeFile(MultipartFile file);

	String loadFile(String fileName);
}
