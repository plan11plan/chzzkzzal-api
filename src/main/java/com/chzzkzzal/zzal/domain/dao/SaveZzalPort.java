package com.chzzkzzal.zzal.domain.dao;

import java.util.Optional;

import com.chzzkzzal.zzal.domain.model.Zzal;

public interface SaveZzalPort {
	Optional<Zzal> findById(Long id);

	void save(Zzal post);
}
