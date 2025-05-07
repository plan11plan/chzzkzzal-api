package com.chzzkzzal.core.auth.infrastructure.jwt;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.TokenProperties;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	private final TokenProperties tokenProperties;
	private final JwtSigningKeyProvider jwtSigningKeyProvider;

	public String generateAccessToken(String memberId) {
		Instant now = Instant.now();
		Instant expiry = now.plus(tokenProperties.expirationTime().accessTokenDuration());

		SecretKey secretKey = jwtSigningKeyProvider.getSigningKey();

		return Jwts.builder()
			.subject(memberId)
			.issuedAt(Date.from(now))
			.expiration(Date.from(expiry))
			.signWith(secretKey)
			.compact();
	}

	public String generateRefreshToken(String externalId) {
		Instant now = Instant.now();
		Instant expiry = now.plus(tokenProperties.expirationTime().refreshTokenDuration());

		SecretKey secretKey = jwtSigningKeyProvider.getSigningKey();

		return Jwts.builder()
			.subject(String.valueOf(externalId))
			.issuedAt(Date.from(now))
			.expiration(Date.from(expiry))
			.signWith(secretKey)
			.compact();
	}

}
