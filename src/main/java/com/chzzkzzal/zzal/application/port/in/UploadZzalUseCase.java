package com.chzzkzzal.zzal.application.port.in;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.application.command.UploadCommand;

@Service
public interface UploadZzalUseCase {
	Long upload(UploadCommand command);
}
