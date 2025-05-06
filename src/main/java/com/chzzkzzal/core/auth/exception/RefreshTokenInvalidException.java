package com.chzzkzzal.core.auth.exception;

import static com.chzzkzzal.core.auth.exception.AuthExceptionCode.*;

import com.chzzkzzal.core.common.error.GlobalException;

public class RefreshTokenInvalidException extends GlobalException {
	public RefreshTokenInvalidException() {
		super(REFRESH_TOKEN_INVALID.getMessage(), REFRESH_TOKEN_INVALID);
	}
}
