package com.chzzkzzal.core.auth.web;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.auth.application.AuthService;
import com.chzzkzzal.core.auth.infrastructure.jwt.TokenProvider;
import com.chzzkzzal.core.auth.service.RefreshTokenService;
import com.chzzkzzal.core.auth.web.response.AccessTokenResponse;
import com.chzzkzzal.core.common.error.CustomResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "인증 API", description = "")
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;
	private final TokenProvider tokenProvider;

	@PostMapping("/reissue")
	@Operation(summary = "액세스 토큰 재발급 API", description = "액세스 토큰 재발급 API [담당자 : 김진수]")
	public CustomResponse<AccessTokenResponse> reissueAccessToken(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		return new CustomResponse<>(authService.reissueTokens(request, response));
	}

	@PostMapping("/logout")
	@Operation(summary = "로그아웃 API", description = "로그아웃 API [담당자 : 김진수]")
	public CustomResponse<Void> logout(
		HttpServletRequest request,
		HttpServletResponse response
	) {
		authService.logout(request, response);
		return CustomResponse.ok();
	}

	private void validateExistHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("SESSION");
		String refreshTokenHeader = request.getHeader(REFRESH_TOKEN.name());
		if (Objects.isNull(authorizationHeader) || Objects.isNull(refreshTokenHeader)) {
			throw new IllegalArgumentException("인증 토큰이 존재하지 않습니다.");
		}
	}

	@Operation(
		summary = "로그인 체크",
		description = "### 로그인 했는지 체크한다.(By http-only) \n"
	)
	@GetMapping("/auth/check")
	public ResponseEntity<CustomResponse<LoginCheckResponse>> checkAuth(HttpServletRequest request) {
		// 1. 요청에서 쿠키 추출
		Cookie[] cookies = request.getCookies();
		String jwtToken = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("SESSION".equals(cookie.getName())) {
					jwtToken = cookie.getValue();
					break;
				}
			}
		}

		// 2. 토큰 검증
		boolean isAuthenticated = false;
		System.out.println(jwtToken);
		if (jwtToken != null) {
			isAuthenticated = tokenProvider.validateToken(jwtToken); // JWT 검증 로직
		}
		System.out.println("로그인 여부 확인");
		System.out.println(isAuthenticated);
		LoginCheckResponse response = new LoginCheckResponse(isAuthenticated);
		return CustomResponse.okResponseEntity(response);
	}

	record LoginCheckResponse(Boolean loggedIn) {
	}
}
