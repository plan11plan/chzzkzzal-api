package com.chzzkzzal.core.auth.infrastructure.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.TokenProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	private final TokenProperties tokenProperties;

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

	public Claims getClaims(String token) {
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
