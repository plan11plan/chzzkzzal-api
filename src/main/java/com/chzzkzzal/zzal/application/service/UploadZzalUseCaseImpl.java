package com.chzzkzzal.zzal.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.core.storage.s3.adapter.in.S3Facade;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.application.command.UploadCommand;
import com.chzzkzzal.zzal.application.port.in.UploadZzalUseCase;
import com.chzzkzzal.zzal.application.port.out.LoadMemberPort;
import com.chzzkzzal.zzal.application.port.out.SaveZzalPort;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.Uploadable;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.domain.zzal.factory.ZzalCreator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadZzalUseCaseImpl implements UploadZzalUseCase {
	private final SaveZzalPort saveZzalPort;
	private final MetadataProvider metadataProvider;
	private final LoadMemberPort loadMemberPort;
	private final S3Facade s3Facade;
	private final ZzalCreatorRouter zzalCreatorRouter;

	@Override
	@Transactional
	public Long upload(UploadCommand command) {
		Member member = loadMemberPort.loadMemberEntity(command.memberId());
		MediaMeta metadata = metadataProvider.getMetadata(command.file());

		ZzalCreator factory = zzalCreatorRouter.getFactory(metadata);

		String fileName = s3Facade.uploadFile(command.file());
		String fileUrl = s3Facade.getFileUrl(fileName);

		Zzal zzal = factory.createZzal(command.channelId(), member, metadata, command.title(), fileUrl);
		if (!(zzal instanceof Uploadable)) {
			throw new IllegalArgumentException("업로드할 수 없습니다.");
		}
		return saveZzalPort.save(zzal).getId();
	}
}
