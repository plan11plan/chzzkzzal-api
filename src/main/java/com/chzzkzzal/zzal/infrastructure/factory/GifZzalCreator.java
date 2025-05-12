package com.chzzkzzal.zzal.infrastructure.factory;// GifZzalCreator.java

import org.springframework.stereotype.Component;

import com.chzzkzzal.zzal.application.port.in.command.SaveZzalCommand;
import com.chzzkzzal.zzal.domain.metadata.Gif;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.GifZzal;
import com.chzzkzzal.zzal.domain.zzal.Zzal;
import com.chzzkzzal.zzal.domain.zzal.factory.ZzalCreator;

@Component
public class GifZzalCreator implements ZzalCreator {
	@Override
	public boolean supports(MediaMeta metadata) {
		return metadata instanceof Gif;
	}

	@Override
	public Zzal createZzal(SaveZzalCommand command) {
		return GifZzal.create(
			command.channelId(),
			command.member(),
			(Gif)command.mediaMeta(),
			command.title(),
			command.fileUrl()
		);
	}
}
