package com.chzzkzzal.zzal.application.port.in.query;

import static com.chzzkzzal.zzalhits.domain.UserAgentAnalyzer.*;

import com.chzzkzzal.zzalhits.domain.ClientIpExtractor;
import com.chzzkzzal.zzalhits.domain.UserAgentAnalyzer;

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
