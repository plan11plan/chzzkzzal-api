package com.chzzkzzal.zzal.application.port.in;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadZzalUseCase {
	Long upload(String channelId, String title, Long memberId, MultipartFile multipartFile);
}
