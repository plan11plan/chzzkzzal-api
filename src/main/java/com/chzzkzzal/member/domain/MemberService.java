package com.chzzkzzal.member.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chzzkzzal.core.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 신규 회원 생성
	 */
	public String signin(String channelId, String channelName) {

		Member member = findOrCreate(channelId, channelName);

		String token = jwtProvider.createAccessToken(member.getChannelId());
		return token;
	}

	public Member findOrCreate(String channelId, String channelName) {

		return memberRepository.findByChannelId(channelId)
			.orElseGet(() -> {
				return createMember(channelId, channelName);
			});
	}

	private Member createMember(String channelId, String channelName) {
		String encryptedPassword = passwordEncoder.encode(channelId);

		Member member = Member.builder()
			.channelId(channelId)
			.password(encryptedPassword)
			.channelName(channelName)
			.build();

		return memberRepository.save(member);
	}

}
