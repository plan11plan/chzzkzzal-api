package com.chzzkzzal.core.auth.application.usecase;

import com.chzzkzzal.core.auth.web.response.AccessTokenResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ReissueTokenUseCase {
	AccessTokenResponse execute(HttpServletRequest request, HttpServletResponse response);
}
