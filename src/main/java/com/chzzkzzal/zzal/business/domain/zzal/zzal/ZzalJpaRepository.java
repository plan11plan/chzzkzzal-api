package com.chzzkzzal.zzal.business.domain.zzal.zzal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

public interface ZzalJpaRepository extends JpaRepository<Zzal, Long> {

	List<Zzal> findAllByChannelId(String channelId);
	
	List<Zzal> findAll();

	Page<Zzal> findAll(Pageable pageable);

	List<Zzal> findByIdLessThan(Long id, Pageable pageable);
}
