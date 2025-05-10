package com.chzzkzzal.zzal.domain.zzal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ZzalJpaRepository extends JpaRepository<Zzal, Long> {

	List<Zzal> findAllByChannelId(String channelId);
}
