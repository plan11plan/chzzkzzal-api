package com.chzzkzzal.zzal.domain.zzal.zzal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chzzkzzal.zzal.domain.zzal.zzal.entity.ZzalHit;

public interface ZzalHitJpaRepository extends JpaRepository<ZzalHit, Long> {
	Long countByZzalId(Long zzalId);

	boolean existsByUniqueKey(String uniqueKey);

}
