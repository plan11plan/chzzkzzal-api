package com.chzzkzzal.zzal.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.core.storage.s3.adapter.in.S3Facade;
import com.chzzkzzal.core.storage.s3.application.command.UploadFileCommand;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.application.port.in.ExtractMetadataUseCase;
import com.chzzkzzal.zzal.application.port.in.UploadZzalUseCase;
import com.chzzkzzal.zzal.application.port.in.command.SaveZzalCommand;
import com.chzzkzzal.zzal.application.port.in.command.UploadCommand;
import com.chzzkzzal.zzal.application.port.in.query.ExtractMetadataQuery;
import com.chzzkzzal.zzal.application.port.out.LoadMemberPort;
import com.chzzkzzal.zzal.domain.zzal.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.zzal.service.ZzalCommandService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadZzalService implements UploadZzalUseCase {
	private final ExtractMetadataUseCase extractMetadataUseCase;
	private final LoadMemberPort loadMemberPort;
	private final S3Facade s3Facade;
	private final ZzalCommandService zzalService;

	@Override
	@Transactional
	public Long upload(UploadCommand command) {
		Member member = loadMemberPort.loadMemberEntity(command.memberId());
		MediaMeta mediaMeta = extractMetadataUseCase.execute(
			new ExtractMetadataQuery(
				command.bytes(),
				command.originalFilename(),
				command.contentType()
			));

		String fileName = s3Facade.uploadFile(
			new UploadFileCommand(
				command.inputStream(),
				command.bytes(),
				command.originalFilename(),
				command.contentType())
		);
		String fileUrl = s3Facade.getFileUrl(fileName);

		return zzalService.save(new SaveZzalCommand(
			command.channelId(),
			member,
			mediaMeta,
			command.title(),
			fileUrl)
		);
	}
}
