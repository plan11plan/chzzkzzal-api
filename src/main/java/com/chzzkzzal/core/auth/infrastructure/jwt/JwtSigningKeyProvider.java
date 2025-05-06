package com.chzzkzzal.core.auth.infrastructure.jwt;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.TokenProperties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtSigningKeyProvider {

	private final TokenProperties tokenProperties;
	private SecretKey cachedSecretKey;

	@PostConstruct
	private void init() {
		byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.secretKey());
		this.cachedSecretKey = Keys.hmacShaKeyFor(keyBytes);
	}

	public SecretKey getSigningKey() {
		return this.cachedSecretKey;
	}
}
