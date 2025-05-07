package com.chzzkzzal.core.auth.application.impl;

import org.springframework.stereotype.Service;

import com.chzzkzzal.common.properties.TokenProperties;
import com.chzzkzzal.core.auth.application.usecase.SignInUseCase;
import com.chzzkzzal.core.auth.application.usecase.dto.SignInCommand;
import com.chzzkzzal.core.auth.domain.repository.SaveMemberPort;
import com.chzzkzzal.core.auth.domain.service.RefreshTokenService;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenInjector;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenProvider;
import com.chzzkzzal.core.auth.web.response.SignInResponse;
import com.chzzkzzal.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChzzkSignInUseCase implements SignInUseCase {
	private final SaveMemberPort saveMemberPort;
	private final RefreshTokenService refreshTokenService;
	private final TokenProvider tokenProvider;
	private final TokenInjector tokenInjector;
	private final TokenProperties tokenProperties;

	@Override
	public SignInResponse execute(final SignInCommand command) {
		Member member = saveMemberPort.save(command.channelId(), command.channelName());
		String accessToken = tokenProvider.generateAccessToken(String.valueOf(member.getId()));
		String refreshToken = refreshTokenService.issueRefreshToken(member);

		//		tokenInjector.injectRefreshTokenToCookie(new TokenResult(refreshToken, String.valueOf(member.getId())),
		//			response);
		//		tokenInjector.addCookie(TokenName.SESSION.name(), accessTokenMinutes,
		//			(int)tokenProperties.expirationTime().accessTokenMinutes(), response);

		return new SignInResponse(member.getChannelName(), accessToken, refreshToken);
	}
}
