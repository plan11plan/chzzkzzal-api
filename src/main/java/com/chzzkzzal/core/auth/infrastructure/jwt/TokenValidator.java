package com.chzzkzzal.core.auth.infrastructure.jwt;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.web.exception.InvalidJwtTokenException;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenValidator {
	private final JwtSigningKeyProvider jwtSigningKeyProvider;

	public void validateToken(String token) {
		SecretKey signingKey = jwtSigningKeyProvider.getSigningKey();
		try {
			Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new InvalidJwtTokenException();
		}
	}

	public boolean isValid(String token) {
		try {
			validateToken(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
