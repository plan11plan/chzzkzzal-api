package com.chzzkzzal.core.auth.web.exception;

import static com.chzzkzzal.core.auth.web.exception.AuthExceptionCode.*;

import com.chzzkzzal.common.error.GlobalException;

public class InvalidJwtTokenException extends GlobalException {
	public InvalidJwtTokenException() {
		super(INVALID_JWT_SIGNATURE.getMessage(), INVALID_JWT_SIGNATURE);
	}
}
