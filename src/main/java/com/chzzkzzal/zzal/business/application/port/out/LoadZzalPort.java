package com.chzzkzzal.zzal.business.application.port.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

public interface LoadZzalPort {
	Optional<Zzal> findById(Long id); // 조회 기능

	List<Zzal> findAll(); // 전체 조회 기능 (필요한 경우 추가)

	Page<Zzal> findAll(Pageable pageable);

	List<Zzal> findByIdLessThan(Long id, Pageable pageable);
}
