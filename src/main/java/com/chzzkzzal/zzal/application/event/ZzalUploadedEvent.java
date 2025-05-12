package com.chzzkzzal.zzal.application.event;

import com.chzzkzzal.zzal.domain.zzal.zzal.ZzalType;

public record ZzalUploadedEvent(Long zzalId, ZzalType type, String filePath) {
}
