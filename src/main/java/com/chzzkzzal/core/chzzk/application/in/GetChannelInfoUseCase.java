package com.chzzkzzal.core.chzzk.application.in;

import com.chzzkzzal.core.chzzk.common.response.ChzzkChannelInfoResponse;

public interface GetChannelInfoUseCase {
	ChzzkChannelInfoResponse getChannelInfoByIds(String[] channelIds);
}
