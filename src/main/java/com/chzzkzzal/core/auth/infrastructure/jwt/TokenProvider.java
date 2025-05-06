package com.chzzkzzal.core.auth.infrastructure.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.TokenProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenProvider {
	private final TokenProperties tokenProperties;
	private final JwtSigningKeyProvider jwtSigningKeyProvider;

	public String generateAccessToken(String memberId) {
		long currentTimeMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeMillis);

		Date expiry = new Date(currentTimeMillis + tokenProperties.expirationTime().accessToken() * 1000);

		SecretKey secretKey = jwtSigningKeyProvider.getSigningKey();

		return Jwts.builder()
			.subject(memberId)
			.issuedAt(now)
			.expiration(expiry)
			.signWith(secretKey)
			.compact();
	}

	public String generateRefreshToken(String externalId) {
		long currentTimeMillis = System.currentTimeMillis();
		Date now = new Date(currentTimeMillis);
		SecretKey secretKey = jwtSigningKeyProvider.getSigningKey();

		return Jwts.builder()
			.issuer(tokenProperties.issuer())
			.subject(String.valueOf(externalId))
			.issuedAt(now)
			.expiration(
				Date.from(Instant.now().plus(tokenProperties.expirationTime().refreshTokenHours(), ChronoUnit.HOURS)))
			.signWith(secretKey)
			.compact();
	}

	public Claims getClaims(String token) {
		SecretKey signingKey = jwtSigningKeyProvider.getSigningKey();

		return Jwts.parser()
			.verifyWith(signingKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
