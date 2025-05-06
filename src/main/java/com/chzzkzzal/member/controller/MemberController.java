package com.chzzkzzal.member.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chzzkzzal.core.auth.domain.MemberUserDetails;
import com.chzzkzzal.core.auth.web.response.SignInResponse;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.member.domain.MemberService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "(미구현) 회원 API", description = "")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	@Value("${chzzkzzal.front}")
	private String FRONT_DOMAIN;

	@Value("${cookie.name}")
	private String COOKIE_NAME;

	@Value("${cookie.domain}")
	private String COOKIE_DOMAIN;

	@Value("${cookie.path}")
	private String COOKIE_PATH;
	@Value("${cookie.days}")
	private int COOKIE_DAYS;
	@Value("${cookie.same_site}")
	private String COOKIE_SAME_SITE;
	private final MemberService memberService;

	@GetMapping("/me")
	public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal MemberUserDetails userDetails) {

		return ResponseEntity.ok(Map.of(
			"channelId", userDetails.getMember().getChannelId(),
			"channelName", userDetails.getMember().getChannelName()
		));
	}

	@PostMapping("mock")
	public ResponseEntity<Object> createMockMember() {
		Member userInfo = memberService.findOrCreate("채널Id", "채널이름");

		SignInResponse signInResponse = memberService.signin(userInfo.getChannelId(), userInfo.getChannelName());

		// JWT 토큰을 HTTP-only 쿠키로 설정
		ResponseCookie cookie = ResponseCookie
			.from(COOKIE_NAME, signInResponse.accessToken())
			.domain(COOKIE_DOMAIN)
			.httpOnly(true)
			.secure(true) // 로컬호스트에서는 false, 프로덕션에서는 true로 설정
			.path(COOKIE_PATH)
			.maxAge(Duration.ofDays(COOKIE_DAYS))
			.sameSite(COOKIE_SAME_SITE) // CSRF 보호
			.build();

		return ResponseEntity.status(HttpStatus.FOUND)
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.header("Refresh-Token", "Bearer " + signInResponse.refreshToken())
			.build();
	}
}
