package com.chzzkzzal.core.auth.application.usecase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LogoutUseCase {
	void execute(HttpServletRequest request, HttpServletResponse response);
}
