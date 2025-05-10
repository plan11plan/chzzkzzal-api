package com.chzzkzzal.zzal.application.port.out;

import java.util.Optional;

import com.chzzkzzal.zzal.domain.zzal.Zzal;

public interface SaveZzalPort {
	Optional<Zzal> findById(Long id);

	Zzal save(Zzal post);
}
