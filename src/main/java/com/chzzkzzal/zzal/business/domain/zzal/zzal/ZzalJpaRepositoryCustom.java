package com.chzzkzzal.zzal.business.domain.zzal.zzal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.chzzkzzal.zzal.business.application.port.out.DeleteZzalPort;
import com.chzzkzzal.zzal.business.application.port.out.LoadZzalPort;
import com.chzzkzzal.zzal.business.application.port.out.SaveZzalPort;
import com.chzzkzzal.zzal.business.domain.zzal.zzal.entity.Zzal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ZzalJpaRepositoryCustom implements SaveZzalPort, LoadZzalPort, DeleteZzalPort {
	private final ZzalJpaRepository jpaRepository;

	@Override
	public void deleteById(Long id) {

	}

	@Override
	public List<Zzal> findAll() {
		return jpaRepository.findAll();
	}

	@Override
	public Page<Zzal> findAll(Pageable pageable) {
		return jpaRepository.findAll(pageable);
	}

	@Override
	public List<Zzal> findByIdLessThan(final Long id, final Pageable pageable) {
		return jpaRepository.findByIdLessThan(id, pageable);
	}

	@Override
	public Optional<Zzal> findById(Long id) {
		return jpaRepository.findById(id);
	}

	@Override
	public Zzal save(Zzal zzal) {
		return jpaRepository.save(zzal);
	}
}
