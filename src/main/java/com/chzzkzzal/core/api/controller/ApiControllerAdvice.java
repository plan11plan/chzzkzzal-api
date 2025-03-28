package com.chzzkzzal.core.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chzzkzzal.core.support.error.CoreException;
import com.chzzkzzal.core.support.error.ErrorType;
import com.chzzkzzal.core.support.response.ApiResponse;

@RestControllerAdvice
public class ApiControllerAdvice {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(CoreException.class)
	public ResponseEntity<ApiResponse<?>> handleCoreException(CoreException e) {
		switch (e.getErrorType().getLogLevel()) {
			case ERROR -> log.error("CoreException : {}", e.getMessage(), e);
			case WARN -> log.warn("CoreException : {}", e.getMessage(), e);
			default -> log.info("CoreException : {}", e.getMessage(), e);
		}
		return new ResponseEntity<>(
			ApiResponse.error(
				e.getErrorType(),
				e.getData()),
			e.getErrorType().getStatus()
		);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
		log.error("Exception : {}", e.getMessage(), e);
		ApiResponse<?> error = ApiResponse.error(ErrorType.DEFAULT_ERROR, e.getMessage());
		return new ResponseEntity<>(error, ErrorType.DEFAULT_ERROR.getStatus());
		// return new ResponseEntity<>(ApiResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.getStatus());
	}

}
