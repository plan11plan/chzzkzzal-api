package com.chzzkzzal.core.auth.web;

import static com.chzzkzzal.core.auth.domain.TokenName.*;

import java.net.URI;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.common.properties.TokenProperties;
import com.chzzkzzal.core.auth.application.usecase.dto.SignInCommand;
import com.chzzkzzal.core.auth.facade.out.AuthFacade;
import com.chzzkzzal.core.auth.web.response.SignInResponse;
import com.chzzkzzal.core.auth.web.support.CookieMaker;
import com.chzzkzzal.core.chzzk.adapter.in.facade.ChzzkDevelopersFacade;
import com.chzzkzzal.core.chzzk.application.service.IssueAccessTokenService;
import com.chzzkzzal.core.chzzk.common.response.ChzzkTokenResponse;
import com.chzzkzzal.core.chzzk.common.response.ChzzkUserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "치지직 Auth API", description = "### 치치직 애플리케이션 API 사용 : "
	+ "https://developers.chzzk.naver.com/application")
public class ChzzkOAuthController {

	@Value("${spring.redirect.url}")
	private String redirectUrl;

	private final IssueAccessTokenService issueAccessTokenService;
	private final ChzzkDevelopersFacade chzzkDevelopersFacade;
	private final AuthFacade authFacade;
	private final CookieMaker cookieMaker;
	private final TokenProperties tokenProperties;

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
	public ResponseEntity<?>
	callback(
		@RequestParam("code") String code,
		@RequestParam("state") String state
	) {
		ChzzkTokenResponse chzzkToken = chzzkDevelopersFacade.issueAccessToken(code, state);
		ChzzkUserResponse chzzkUserResponse = chzzkDevelopersFacade.getUserChannelInfo(chzzkToken.accessToken());

		SignInCommand command = new SignInCommand(
			chzzkUserResponse.channelId(),
			chzzkUserResponse.channelName()
		);
		SignInResponse result = authFacade.signIn(command);

		ResponseCookie accessCookie = cookieMaker.makeCookie(
			SESSION.name(),
			result.accessToken(),
			Duration.ofMinutes(tokenProperties.expirationTime().accessTokenMinutes())
		);
		ResponseCookie refreshCookie = cookieMaker.makeCookie(
			REFRESH_TOKEN.name(),
			result.accessToken(),
			Duration.ofHours(tokenProperties.expirationTime().refreshTokenHours())
		);

		return ResponseEntity.status(HttpStatus.FOUND)
			.location(URI.create(redirectUrl))
			.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
			.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
			.build();
	}
}
