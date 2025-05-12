package com.chzzkzzal.zzal.application.port.out;

import com.chzzkzzal.core.storage.s3.application.command.UploadFileCommand;

public interface FileStoragePort {
	String upload(UploadFileCommand command);

	String getUrl(String fileName);
}
