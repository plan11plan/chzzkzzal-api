package com.chzzkzzal.core.auth.application.usecase;

import com.chzzkzzal.core.auth.application.usecase.dto.SignInCommand;

import jakarta.servlet.http.HttpServletResponse;

public interface SignInUseCase {
	void execute(HttpServletResponse response, SignInCommand command);
}
