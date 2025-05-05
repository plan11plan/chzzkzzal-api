package com.chzzkzzal.core.security.application;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.common.properties.TokenProperties;
import com.chzzkzzal.core.security.dto.AccessTokenResponse;
import com.chzzkzzal.core.security.service.AccessTokenService;
import com.chzzkzzal.core.security.service.RefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final RefreshTokenService refreshTokenService;
	private final TokenProperties tokenProperties;
	private final AccessTokenService accessTokenService;

	public AccessTokenResponse reissueTokens(HttpServletRequest request, HttpServletResponse response) {
		String externalId = refreshTokenService.reissueRefreshToken(request, response);
		AccessTokenResponse accessTokenResponse = createAccessTokenResponse(externalId);

		logSuccessfulReissue(externalId, request.getRemoteAddr());
		return accessTokenResponse;
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		refreshTokenService.expireRefreshToken(request, response);
		log.info("User {} logged out", maskId(request.getRemoteAddr()));
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
