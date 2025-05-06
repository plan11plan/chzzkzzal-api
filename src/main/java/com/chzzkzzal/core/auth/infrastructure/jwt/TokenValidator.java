package com.chzzkzzal.core.auth.infrastructure.jwt;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.TokenProperties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenValidator {
	private final TokenProperties tokenProperties;

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = tokenProperties.secretKey().getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
