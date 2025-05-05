package com.chzzkzzal.core.security.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.core.security.domain.RefreshToken;
import com.chzzkzzal.core.security.domain.RefreshTokenRepository;
import com.chzzkzzal.core.security.exception.RefreshTokenInvalidException;
import com.chzzkzzal.core.security.infrastructure.jwt.TokenInjector;
import com.chzzkzzal.core.security.infrastructure.jwt.TokenProvider;
import com.chzzkzzal.core.security.infrastructure.jwt.TokenResolver;
import com.chzzkzzal.core.security.infrastructure.jwt.TokenResult;
import com.chzzkzzal.member.domain.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final TokenProvider tokenProvider;
	private final TokenInjector tokenInjector;
	private final TokenResolver tokenResolver;
	private final AccessTokenService accessTokenService;
	private final RefreshTokenRepository refreshTokenRepository;

	/**
	 * 클라이언트가 제공한 Refresh Token 기반으로 새로운 Access Token 및 Refresh Token을 재발급하고,
	 * Refresh Token은 HttpOnly 쿠키로 응답에 주입합니다.
	 *
	 * 처리 순서는 다음과 같습니다:
	 *     요청에서 refresh token을 추출 (쿠키 또는 헤더)
	 *     해당 토큰이 DB에 존재하는지 확인
	 *     RefreshToken을 회전(rotate)하여 새로운 refresh token 발급 및 저장
	 *     TokenResult(새로운 리프레시 토큰 및 외부 식별자)를 응답에 주입
	 *     외부 식별자(channelId 또는 memberId)를 반환
	 *
	 * @param request  HttpServletRequest (쿠키 또는 헤더로부터 refresh token 추출에 사용)
	 * @param response HttpServletResponse (새로운 refresh token을 HttpOnly 쿠키로 응답에 설정)
	 * @return 외부 식별자 (예: 사용자 ID, 채널 ID 등. access token 재발급에 활용 가능)
	 * @throws RefreshTokenInvalidException 요청에 refresh token이 없거나 잘못된 경우 발생
	 * @throws IllegalAccessError           DB에 해당 refresh token이 존재하지 않는 경우 발생
	 */
	@Transactional
	public String reissueRefreshToken(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		String rawToken = extractRefreshTokenOrThrow(request);
		RefreshToken refreshToken = getRefreshTokenByValue(rawToken);

		TokenResult tokenResult = rotateRefreshToken(refreshToken);
		injectTokenToResponse(tokenResult, response);

		return tokenResult.externalId();
	}

	@Transactional
	public String issueRefreshToken(Member member) {
		String refreshToken = tokenProvider.generateRefreshToken();
		saveOrUpdateRefreshToken(member, refreshToken);
		return refreshToken;
	}

	private String extractRefreshTokenOrThrow(HttpServletRequest request) {
		return tokenResolver.resolveRefreshTokenFromRequest(request)
			.orElseThrow(() -> {
				log.warn("재발급 실패: 요청에 refresh token이 없음 (IP: {})", request.getRemoteAddr());
				return new RefreshTokenInvalidException();
			});
	}

	private TokenResult rotateRefreshToken(RefreshToken refreshToken) {
		String reissuedToken = rotate(refreshToken);
		return new TokenResult(reissuedToken, refreshToken.getExternalId());
	}

	private void injectTokenToResponse(TokenResult tokenResult, HttpServletResponse response) {
		tokenInjector.injectRefreshTokenToCookie(tokenResult, response);
	}

	private String rotate(RefreshToken refreshToken) {
		String reissuedToken = tokenProvider.generateRefreshToken();
		RefreshToken rotatedToken = refreshToken.rotate(reissuedToken);
		refreshTokenRepository.save(rotatedToken);
		return reissuedToken;
	}

	private void saveOrUpdateRefreshToken(Member member, String refreshToken) {
		Long memberId = member.getId();
		refreshTokenRepository.findById(String.valueOf(memberId))
			.ifPresentOrElse(
				it -> it.rotate(refreshToken),
				() -> refreshTokenRepository.save(RefreshToken.of(String.valueOf(memberId), refreshToken))
			);
	}

	/**
	 * 1) Refresh Token 검증
	 * 2) channelId 추출
	 * 3) DB 조회
	 * 4) 새 Access Token 발급
	 */
	public RefreshToken getRefreshTokenByValue(String value) {
		return refreshTokenRepository
			.findByToken(value)
			.orElseThrow(IllegalAccessError::new);
	}

	public void expireRefreshToken(HttpServletRequest request, HttpServletResponse response) {
		Optional<String> tokenString = tokenResolver.resolveRefreshTokenFromRequest(request);
		isvalidRefreshToken(request, tokenString);
		tokenInjector.invalidateCookie("REFRESH_TOKEN", response);
	}

	private static void isvalidRefreshToken(
		HttpServletRequest request, Optional<String> tokenString) {
		if (tokenString.isEmpty()) {
			log.warn(
				"Failed to reissue token: No refresh token found in request from IP {}",
				request.getRemoteAddr());
			throw new RefreshTokenInvalidException();
		}
	}
}
