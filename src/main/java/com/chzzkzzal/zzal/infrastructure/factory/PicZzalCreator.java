package com.chzzkzzal.zzal.infrastructure.factory;// PicZzalCreator.java

import org.springframework.stereotype.Component;

import com.chzzkzzal.member.domain.Member;
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
	public Zzal createZzal(String channelId, Member member, MediaMeta metadata, String title, String url) {
		return PicZzal.create(channelId, member, (Pic)metadata, title, url);
	}
}
