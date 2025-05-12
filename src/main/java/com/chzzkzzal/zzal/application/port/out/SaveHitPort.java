package com.chzzkzzal.zzal.application.port.out;

import com.chzzkzzal.zzal.domain.zzal.zzal.entity.ZzalHit;

public interface SaveHitPort {
	void saveIgnoreDuplicate(ZzalHit hit);

}
