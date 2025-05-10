package com.chzzkzzal.zzal.application.result;

import java.time.LocalDateTime;

import com.chzzkzzal.member.application.query.MemberInfo;
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

	public static ZzalDetailResponse toResponse(Zzal zzal, MemberInfo member) {
		return new ZzalDetailResponse(
			zzal.getId(),
			zzal.getUrl(),
			zzal.getTitle(),
			zzal.getCreatedAt(),
			zzal.getUpdatedAt(),
			member.id(),
			member.channelName(),
			zzal.getMetaInfo()
		);
	}
}
