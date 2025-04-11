package com.chzzkzzal.streamer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chzzkzzal.member.domain.MemberRepository;
import com.chzzkzzal.zzal.domain.model.zzal.Zzal;
import com.chzzkzzal.zzal.domain.service.ZzalDetailResponse;
import com.chzzkzzal.zzal.infrastructure.persistence.jpa.ZzalJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StreamerService {
	private final StreamerRepository streamerRepository;
	private final ZzalJpaRepository zzalJpaRepository;
	private final MemberRepository memberRepository;

	public Long register(RegisterStreamerCommand command) {
		Streamer streamer = Streamer.register(
			command.channelId(),
			command.channelName(),
			command.channelImageUrl(),
			command.followerCount()
		);
		streamer = streamerRepository.save(streamer);
		return streamer.getId();
	}

	public List<GetStreamerResponse> findAll() {
		return streamerRepository.findAll().stream().map(streamer -> new GetStreamerResponse(
			streamer.getId(),
			streamer.getChannelId(),
			streamer.getChannelName(),
			streamer.getChannelImageUrl(),
			streamer.getFollowerCount(),
			streamer.getCreatedAt(),
			streamer.getUpdatedAt()
		)).collect(Collectors.toList());
	}

	public GetStreamerResponse findById(Long streamerId) {
		Streamer streamer = streamerRepository.findById(streamerId).orElseThrow(() -> new IllegalArgumentException());
		return new GetStreamerResponse(
			streamer.getId(),
			streamer.getChannelId(),
			streamer.getChannelName(),
			streamer.getChannelImageUrl(),
			streamer.getFollowerCount(),
			streamer.getCreatedAt(),
			streamer.getUpdatedAt()
		);

	}

	public List<ZzalDetailResponse> getStreamerZzals(Long streamerId) {
		List<Zzal> zzals = zzalJpaRepository.findAllByStreamerId(streamerId);
		List<ZzalDetailResponse> response = zzals.stream()
			.map(zzal -> ZzalDetailResponse.toResponse(zzal, zzal.getMember()))
			.collect(Collectors.toList());

		return response;
	}
}
