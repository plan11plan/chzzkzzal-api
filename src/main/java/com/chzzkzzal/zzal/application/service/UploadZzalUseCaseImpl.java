package com.chzzkzzal.zzal.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.storage.s3.adapter.in.S3Facade;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberLoader;
import com.chzzkzzal.zzal.application.port.in.UploadZzalUseCase;
import com.chzzkzzal.zzal.application.port.out.SaveZzalPort;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.domain.zzal.factory.ZzalCreator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadZzalUseCaseImpl implements UploadZzalUseCase {
	private final SaveZzalPort saveZzalPort;
	private final MetadataProvider metadataProvider;
	private final MemberLoader memberLoader;
	private final S3Facade s3Facade;
	private final ZzalCreatorRouter zzalCreatorRouter;

	@Override
	@Transactional
	public Long upload(String channelId, String title, Long memberId, MultipartFile multipartFile) {
		Member member = memberLoader.loadMember(memberId);
		MediaMeta metadata = metadataProvider.getMetadata(multipartFile);

		ZzalCreator factory = zzalCreatorRouter.getFactory(metadata);

		String fileName = s3Facade.uploadFile(multipartFile);
		String fileUrl = s3Facade.getFileUrl(fileName);

		Zzal zzal = factory.createZzal(channelId, member, metadata, title, fileUrl);
		return saveZzalPort.save(zzal).getId();
	}
}
