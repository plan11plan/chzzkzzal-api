package com.chzzkzzal.core.auth.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chzzkzzal.core.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
	//	Optional<RefreshToken> findByMemberIdAndReissueCountLessThan(String id, long count);

	Optional<RefreshToken> findByToken(String token);

	boolean deleteByToken(String token);
}
