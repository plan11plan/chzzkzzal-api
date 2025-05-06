package com.chzzkzzal.core.auth.application.authenticator;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.infrastructure.jwt.JwtClaimsExtractor;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenAuthenticator {
	private final TokenProvider tokenProvider;
	private final UserDetailsService userDetailsService;
	private final JwtClaimsExtractor jwtClaimsExtractor;

	public Authentication authenticate(String token) {
		Claims claims = jwtClaimsExtractor.extractClaims(token);
		String subject = claims.getSubject();
		UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

		return new UsernamePasswordAuthenticationToken(
			userDetails, token, userDetails.getAuthorities()
		);
	}
}
