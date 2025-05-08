package com.chzzkzzal.core.chzzk.application.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.core.chzzk.application.in.GetUserChannelInfoUseCase;
import com.chzzkzzal.core.chzzk.application.out.ChzzkDevelopersApiPort;
import com.chzzkzzal.core.chzzk.common.response.ChzzkUserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserChannelInfoService implements GetUserChannelInfoUseCase {
	private final ChzzkDevelopersApiPort api;

	@Override
	public ChzzkUserResponse getUserChannelInfo(String token) {
		return api.requestUserChannelInfo(token);
	}
}
