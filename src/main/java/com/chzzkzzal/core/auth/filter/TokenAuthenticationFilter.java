package com.chzzkzzal.core.auth.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.application.authenticator.TokenAuthenticator;
import com.chzzkzzal.core.auth.domain.TokenName;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenResolver;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenValidator;
import com.chzzkzzal.core.auth.web.exception.MissingJwtTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter implements AuthenticationFilter {

	private final TokenValidator tokenValidator;
	private final TokenAuthenticator tokenAuthenticator;
	private final TokenResolver tokenResolver;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String token = tokenResolver
			.resolveFromCookie(request, TokenName.SESSION.name())
			.orElseThrow(MissingJwtTokenException::new);

		log.info("Extracted token: {}", token);

		tokenValidator.validateToken(token);

		Authentication authentication = tokenAuthenticator.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.info("Authentication principal: {}", authentication.getPrincipal());
		log.info("Token validated successfully");

		filterChain.doFilter(request, response);
	}

}
