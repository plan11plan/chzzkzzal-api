package com.chzzkzzal.zzal.application.event;

import com.chzzkzzal.zzal.domain.zzal.zzal.entity.ZzalType;

public record ZzalUploadedEvent(Long zzalId, ZzalType type, String filePath) {
}
