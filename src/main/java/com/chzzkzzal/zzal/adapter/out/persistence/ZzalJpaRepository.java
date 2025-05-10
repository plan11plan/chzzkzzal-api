package com.chzzkzzal.zzal.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chzzkzzal.zzal.domain.zzal.Zzal;

public interface ZzalJpaRepository extends JpaRepository<Zzal, Long> {

	List<Zzal> findAllByChannelId(String channelId);
}
