package com.chzzkzzal.zzal.infrastructure.factory;// GifZzalCreator.java

import org.springframework.stereotype.Component;

import com.chzzkzzal.member.domain.Member;
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
	public Zzal createZzal(String channelId, Member member, MediaMeta metadata, String title, String url) {
		return GifZzal.create(channelId, member, (Gif)metadata, title, url);
	}
}
