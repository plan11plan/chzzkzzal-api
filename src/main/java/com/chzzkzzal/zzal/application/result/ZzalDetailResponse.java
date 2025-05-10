package com.chzzkzzal.zzal.application.result;

import java.time.LocalDateTime;

import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.Zzal;

public record ZzalDetailResponse(
	Long zzalId,
	String url,
	String title,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	Long writerId,
	String writerChannelName,
	MediaMeta mediaMeta

) {

	public static ZzalDetailResponse toResponse(Zzal zzal, Member member) {
		return new ZzalDetailResponse(
			zzal.getId(),
			zzal.getUrl(),
			zzal.getTitle(),
			zzal.getCreatedAt(),
			zzal.getUpdatedAt(),
			member.getId(),
			member.getChannelName(),
			zzal.getMetaInfo()
		);
	}
}
