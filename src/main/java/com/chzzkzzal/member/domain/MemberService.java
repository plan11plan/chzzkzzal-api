package com.chzzkzzal.member.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.core.auth.domain.service.RefreshTokenService;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenProvider;
import com.chzzkzzal.core.auth.web.response.SignInResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final RefreshTokenService refreshTokenService;
	private final TokenProvider tokenProvider;

	/**
	 * 신규 회원 생성
	 * - 회원 저장
	 * - accessToken, refreshToken 발행
	 */
	@Transactional
	public SignInResponse signin(String channelId, String channelName) {
		Member member = findOrCreate(channelId, channelName);
		String accessToken = tokenProvider.generateAccessToken(String.valueOf(member.getId()));
		String refreshToken = refreshTokenService.issueRefreshToken(member);
		return new SignInResponse(member.getChannelName(), accessToken, refreshToken);
	}

	public Member findOrCreate(String channelId, String channelName) {
		return memberRepository.findByChannelId(channelId)
			.orElseGet(() -> createMember(channelId, channelName));
	}

	private Member createMember(String channelId, String channelName) {

		Member member = Member.builder()
			.channelId(channelId)
			.channelName(channelName)
			.build();

		return memberRepository.save(member);
	}

}
