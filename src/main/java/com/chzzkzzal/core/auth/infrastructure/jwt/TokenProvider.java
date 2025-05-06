package com.chzzkzzal.core.auth.infrastructure.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.domain.MemberUserDetails;
import com.chzzkzzal.core.auth.domain.RefreshTokenRepository;
import com.chzzkzzal.core.auth.service.MemberUserDetailService;
import com.chzzkzzal.core.common.properties.TokenProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	private final TokenProperties tokenProperties;
	private final MemberUserDetailService memberUserDetailService;
	private final RefreshTokenRepository refreshTokenRepository;
	private final ObjectMapper objectMapper;
	private final long reissueLimit = 10;

	// 예시: 1일
	private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24;

	public String createAccessToken(String memberId) {
		long currentTimeMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeMillis);

		Date expiry = new Date(currentTimeMillis + tokenProperties.expirationTime().accessToken() * 1000);

		SecretKey secretKey = getSigningKey();

		return Jwts.builder()
			.subject(memberId)
			.issuedAt(now)
			.expiration(expiry)
			.signWith(secretKey)
			.compact();
	}

	public String createAccessToken(Long memberId) {
		long currentTimeMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeMillis);

		Date expiry = new Date(currentTimeMillis + tokenProperties.expirationTime().accessToken() * 1000);

		SecretKey secretKey = getSigningKey();

		return Jwts.builder()
			.subject(String.valueOf(memberId))
			.issuedAt(now)
			.expiration(expiry)
			.signWith(secretKey)
			.compact();
	}

	//	@Transactional
	//	public String recreateAccessToken(String oldAccessToken) throws JsonProcessingException {
	//		String subject = decodeJwtPayloadSubject(oldAccessToken);
	//		refreshTokenRepository.findByMemberIdAndReissueCountLessThan(UUID.fromString(subject.split(":")[0]),
	//				reissueLimit)
	//			.ifPresentOrElse(
	//				RefreshToken::increaseReissueCount,
	//				() -> {
	//					throw new ExpiredJwtException(null, null, "Refresh token expired.");
	//				}
	//			);
	//		return createAccessToken(subject);
	//	}
	//
	//	private String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
	//		return objectMapper.readValue(
	//			new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
	//			Map.class
	//		).get("sub").toString();
	//	}

	/**
	 * 리프레시 토큰은 사용자와 관련된 정보를 전혀 담지 않을 것이기 때문에 subject는 따로 설정하지 않고 발급자와 발급시간, 만료시간만 설정한다.
	 */
	public String generateRefreshToken() {
		long currentTimeMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeMillis);
		SecretKey secretKey = getSigningKey();

		return Jwts.builder()
			.issuer(tokenProperties.issuer())
			.issuedAt(now)
			.expiration(
				Date.from(Instant.now().plus(tokenProperties.expirationTime().refreshTokenHours(), ChronoUnit.HOURS)))
			.signWith(secretKey)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		String channelId = claims.getSubject();

		MemberUserDetails userDetails = memberUserDetailService.loadUserByUsername(channelId);
		return new UsernamePasswordAuthenticationToken(
			userDetails,
			token,
			userDetails.getAuthorities()
		);
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
			.verifyWith(this.getSigningKey())
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = tokenProperties.secretKey().getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
