package com.chzzkzzal.core.auth.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.command.SignInCommand;
import com.chzzkzzal.core.auth.application.port.in.SignInUseCase;
import com.chzzkzzal.core.auth.application.port.out.SaveMemberPort;
import com.chzzkzzal.core.auth.application.port.out.TokenGeneratorPort;
import com.chzzkzzal.core.auth.application.result.SignInResponse;
import com.chzzkzzal.core.auth.domain.RefreshToken;
import com.chzzkzzal.core.auth.domain.RefreshTokenStorePort;
import com.chzzkzzal.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChzzkSignInUseCase implements SignInUseCase {
	private final SaveMemberPort saveMemberPort;
	private final RefreshTokenStorePort refreshTokenStorePort;
	private final TokenGeneratorPort tokenGeneratorPort;

	@Override
	public SignInResponse execute(final SignInCommand command) {
		Member member = saveMemberPort.save(command.channelId(), command.channelName());
		String accessToken = tokenGeneratorPort.generateAccessToken(String.valueOf(member.getId()));
		String refreshToken = tokenGeneratorPort.generateRefreshToken(String.valueOf(member.getId()));
		refreshTokenStorePort.save(RefreshToken.of(String.valueOf(member.getId()), refreshToken));

		return new SignInResponse(member.getChannelName(), accessToken, refreshToken);
	}
}
