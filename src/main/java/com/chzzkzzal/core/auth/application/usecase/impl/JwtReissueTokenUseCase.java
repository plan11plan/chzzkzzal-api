package com.chzzkzzal.core.auth.application.usecase.impl;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.usecase.ReissueTokenUseCase;
import com.chzzkzzal.core.auth.domain.service.AccessTokenService;
import com.chzzkzzal.core.auth.domain.service.RefreshTokenService;
import com.chzzkzzal.core.auth.web.response.AccessTokenResponse;
import com.chzzkzzal.core.common.properties.TokenProperties;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtReissueTokenUseCase implements ReissueTokenUseCase {
	private final RefreshTokenService refreshTokenService;
	private final TokenProperties tokenProperties;
	private final AccessTokenService accessTokenService;

	@Override
	public AccessTokenResponse execute(final HttpServletRequest request, final HttpServletResponse response) {
		String externalId = refreshTokenService.reissueRefreshToken(request, response);
		AccessTokenResponse accessTokenResponse = createAccessTokenResponse(externalId);

		logSuccessfulReissue(externalId, request.getRemoteAddr());
		return accessTokenResponse;
	}

	private AccessTokenResponse createAccessTokenResponse(String externalId) {
		String accessToken = accessTokenService.issueAccessToken(externalId);
		return AccessTokenResponse.of(accessToken, tokenProperties);
	}

	private void logSuccessfulReissue(String externalId, String ip) {
		log.info("User {} accessed from IP {} and successfully reissued a token", maskId(externalId), ip);
	}

	private String maskId(String id) {
		if (id == null || id.length() < 4)
			return "****";
		return id.substring(0, 4) + "****";
	}
}
