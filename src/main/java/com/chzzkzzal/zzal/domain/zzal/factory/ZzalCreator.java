package com.chzzkzzal.zzal.domain.zzal.factory;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.Zzal;

// ZzalCreator.java
public interface ZzalCreator {
	boolean supports(MediaMeta metadata);

	Zzal createZzal(String channelId, Member member, MediaMeta metadata, String title, String url);
}

