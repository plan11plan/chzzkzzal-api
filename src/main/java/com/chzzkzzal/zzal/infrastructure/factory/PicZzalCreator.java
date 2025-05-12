package com.chzzkzzal.zzal.infrastructure.factory;// PicZzalCreator.java

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.application.port.in.command.SaveZzalCommand;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.metadata.Pic;
import com.chzzkzzal.zzal.domain.zzal.PicZzal;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.domain.zzal.factory.ZzalCreator;

@Component
public class PicZzalCreator implements ZzalCreator {
	@Override
	public boolean supports(MediaMeta metadata) {
		return metadata instanceof Pic;
	}

	@Override
	public Zzal createZzal(SaveZzalCommand command) {
		return PicZzal.create(
			command.channelId(),
			command.member(),
			(Pic)command.mediaMeta(),
			command.title(),
			command.fileUrl()
		);
	}
}
