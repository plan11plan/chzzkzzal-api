package com.chzzkzzal.core.security.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
	//	Optional<RefreshToken> findByMemberIdAndReissueCountLessThan(String id, long count);

	Optional<RefreshToken> findByToken(String token);
}
