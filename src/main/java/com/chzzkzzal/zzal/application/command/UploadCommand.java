package com.chzzkzzal.zzal.application.command;

import org.springframework.web.multipart.MultipartFile;

public record UploadCommand(
	String channelId,
	String title,
	Long memberId,
	MultipartFile file
) {
}
