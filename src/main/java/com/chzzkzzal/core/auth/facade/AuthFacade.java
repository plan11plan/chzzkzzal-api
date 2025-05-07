package com.chzzkzzal.core.auth.facade;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.impl.ChzzkSignInUseCase;
import com.chzzkzzal.core.auth.application.usecase.CheckLoginStatusUseCase;
import com.chzzkzzal.core.auth.application.usecase.LogoutUseCase;
import com.chzzkzzal.core.auth.application.usecase.ReissueTokenUseCase;
import com.chzzkzzal.core.auth.application.usecase.dto.SignInCommand;
import com.chzzkzzal.core.auth.web.response.AccessTokenResponse;
import com.chzzkzzal.core.auth.web.response.LoginCheckResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacade {
	private final ReissueTokenUseCase reissueTokenUseCase;
	private final LogoutUseCase logoutUseCase;
	private final CheckLoginStatusUseCase checkLoginStatusUseCase;
	private final ChzzkSignInUseCase chzzkSignInUseCase;

	public AccessTokenResponse reissueTokens(HttpServletRequest request, HttpServletResponse response) {
		return reissueTokenUseCase.execute(request, response);
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		logoutUseCase.execute(request, response);
	}

	public LoginCheckResponse checkLoginStatus(HttpServletRequest request) {
		return checkLoginStatusUseCase.execute(request);
	}

	public void signIn(HttpServletResponse response, SignInCommand command) {
		chzzkSignInUseCase.execute(response, command);
	}

}
