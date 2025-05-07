package com.chzzkzzal.core.auth.application.usecase;

import com.chzzkzzal.core.auth.application.usecase.dto.SignInCommand;
import com.chzzkzzal.core.auth.web.response.SignInResponse;

public interface SignInUseCase {
	SignInResponse execute(SignInCommand command);
}
