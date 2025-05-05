package com.chzzkzzal.core.security.exception;

import static com.chzzkzzal.core.security.exception.AuthExceptionCode.*;

import com.chzzkzzal.core.common.error.GlobalException;

public class RefreshTokenInvalidException extends GlobalException {
	public RefreshTokenInvalidException() {
		super(REFRESH_TOKEN_INVALID.getMessage(), REFRESH_TOKEN_INVALID);
	}
}
