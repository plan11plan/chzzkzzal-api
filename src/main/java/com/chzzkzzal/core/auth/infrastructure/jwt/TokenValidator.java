package com.chzzkzzal.core.auth.infrastructure.jwt;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenValidator {
	private final JwtSigningKeyProvider jwtSigningKeyProvider;

	public boolean validateToken(String token) {
		SecretKey signingKey = jwtSigningKeyProvider.getSigningKey();
		try {
			Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
