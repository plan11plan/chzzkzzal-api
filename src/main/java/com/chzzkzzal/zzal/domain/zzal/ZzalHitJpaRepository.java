package com.chzzkzzal.zzal.domain.zzal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ZzalHitJpaRepository extends JpaRepository<ZzalHit, Long> {
	Long countByZzalId(Long zzalId);

	boolean existsByUniqueKey(String uniqueKey);

}
