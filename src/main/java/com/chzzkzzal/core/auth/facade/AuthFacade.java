package com.chzzkzzal.core.auth.facade;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.auth.application.usecase.LogoutUseCase;
import com.chzzkzzal.core.auth.application.usecase.ReissueTokenUseCase;
import com.chzzkzzal.core.auth.web.response.AccessTokenResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacade {
	private final ReissueTokenUseCase reissueTokenUseCase;
	private final LogoutUseCase logoutUseCase;

	public AccessTokenResponse reissueTokens(HttpServletRequest request, HttpServletResponse response) {
		return reissueTokenUseCase.execute(request, response);
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		logoutUseCase.execute(request, response);
	}
}
