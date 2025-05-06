package com.chzzkzzal.core.auth.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.common.error.CustomResponse;
import com.chzzkzzal.core.auth.facade.AuthFacade;
import com.chzzkzzal.core.auth.web.response.AccessTokenResponse;
import com.chzzkzzal.core.auth.web.response.LoginCheckResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

	private final AuthFacade authFacade;

	@Operation(summary = "액세스 토큰 재발급 API", description = "액세스 토큰 재발급 API [담당자 : 김진수]")
	@PostMapping("/reissue")
	public ResponseEntity<CustomResponse<AccessTokenResponse>>
	reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
		return CustomResponse.okResponseEntity(authFacade.reissueTokens(request, response));
	}

	@Operation(summary = "로그아웃 API", description = "로그아웃 API [담당자 : 김진수]")
	@PostMapping("/logout")
	public ResponseEntity<CustomResponse<Void>>
	logout(HttpServletRequest request, HttpServletResponse response) {
		authFacade.logout(request, response);
		return CustomResponse.okResponseEntity();
	}

	@Operation(summary = "로그인 체크", description = "로그인 상태 체크 API [담당자 : 김진수]")
	@GetMapping("/auth/check")
	public ResponseEntity<CustomResponse<LoginCheckResponse>>
	checkAuth(HttpServletRequest request) {
		return CustomResponse.okResponseEntity(authFacade.checkLoginStatus(request));
	}

}
