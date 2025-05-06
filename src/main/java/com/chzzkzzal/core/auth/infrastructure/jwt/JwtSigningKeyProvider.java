package com.chzzkzzal.core.auth.infrastructure.jwt;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.chzzkzzal.common.properties.TokenProperties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtSigningKeyProvider {

	private final TokenProperties tokenProperties;

	public SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.secretKey());
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
