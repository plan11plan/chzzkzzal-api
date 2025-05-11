package com.chzzkzzal.zzal.application.dto;

import static com.chzzkzzal.zzal.adapter.out.hit.UserAgentAnalyzer.*;

import com.chzzkzzal.zzal.adapter.out.hit.ClientIpExtractor;
import com.chzzkzzal.zzal.adapter.out.hit.UserAgentAnalyzer;

import jakarta.servlet.http.HttpServletRequest;

public record ClientInfo(
	String ipAddress,
	String userAgent,
	BrowserType browserType,
	DeviceType deviceType
) {

	public static ClientInfo from(HttpServletRequest request) {
		String ipAddress = ClientIpExtractor.extractIpAddress(request);

		UserAgentAnalyzer.UserAgentInfo userAgentInfo = analyze(request);
		String userAgent = userAgentInfo.userAgent();
		BrowserType browserType = userAgentInfo.browserType();
		DeviceType deviceType = userAgentInfo.deviceType();

		return new ClientInfo(
			ipAddress,
			userAgent,
			browserType,
			deviceType
		);
	}
}
