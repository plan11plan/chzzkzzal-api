package com.chzzkzzal.core.auth.application.usecase;

import com.chzzkzzal.core.auth.web.response.LoginCheckResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface CheckLoginStatusUseCase {
	LoginCheckResponse execute(HttpServletRequest request);

}
