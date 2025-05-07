package com.chzzkzzal.core.auth.web;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.common.error.CustomResponse;
import com.chzzkzzal.core.auth.application.usecase.dto.SignInCommand;
import com.chzzkzzal.core.auth.facade.AuthFacade;
import com.chzzkzzal.core.auth.web.response.SignInResponse;
import com.chzzkzzal.core.external.chzzk.intrastructure.http.auth.AccessTokenHttpClient;
import com.chzzkzzal.core.external.chzzk.intrastructure.http.user.ChzzkUserHttpClient;
import com.chzzkzzal.member.dto.ChzzkTokenResponse;
import com.chzzkzzal.member.dto.ChzzkUserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "치지직 Auth API", description = "### 치치직 애플리케이션 API 사용 : "
	+ "https://developers.chzzk.naver.com/application")
public class ChzzkOAuthController {
	@Value("${cookie.name}")
	private String COOKIE_NAME;

	@Value("${cookie.domain}")
	private String COOKIE_DOMAIN;

	private final AccessTokenHttpClient accessTokenHttpClient;
	private final ChzzkUserHttpClient userHttpClient;
	private final AuthFacade authFacade;

	@Operation(
		summary = "치지직 AccessToken 발급 및 로그인",
		description =
			"### 로그인 프로세스\n" +
				"1. 유저가 치지직 동의화면 모달창으로부터 동의하기 누르면 치지직 애플리케이션에 등록한 redirectUrl로 code, state와 함께 리다이렉트\n" +
				"2. 백엔드에서 code, state 정보로 AccessToken 발급\n" +
				"3. 첫 로그인시 회원가입 동작\n" +
				"4. 프론트 홈화면으로 리다이렉트 및 JWT 토큰 발급"
	)
	@GetMapping("${chzzk.oauth.redirection-url}")
	public ResponseEntity<CustomResponse<SignInResponse>>
	callback(
		@RequestParam("code") String code,
		@RequestParam("state") String state,
		HttpServletResponse response
	) {
		ChzzkTokenResponse chzzkToken = accessTokenHttpClient.getAccessToken(code, state);
		ChzzkUserResponse chzzkUserResponse = userHttpClient.me(chzzkToken.accessToken());
		SignInCommand command = new SignInCommand(
			chzzkUserResponse.channelId(),
			chzzkUserResponse.channelName()
		);
		authFacade.signIn(response, command);

		return ResponseEntity
			.status(HttpStatus.FOUND)
			.location(URI.create("http://localhost:3000"))
			.build();
	}
}
