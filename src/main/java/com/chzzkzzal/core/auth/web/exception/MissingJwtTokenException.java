package com.chzzkzzal.core.auth.web.exception;

import com.chzzkzzal.common.error.GlobalException;

public class MissingJwtTokenException extends GlobalException {
	public MissingJwtTokenException() {
		super(AuthExceptionCode.MISSING_JWT_TOKEN.getMessage(), AuthExceptionCode.MISSING_JWT_TOKEN);
	}
}
