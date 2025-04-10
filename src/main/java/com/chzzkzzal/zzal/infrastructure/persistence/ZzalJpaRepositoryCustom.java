package com.chzzkzzal.zzal.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.chzzkzzal.zzal.domain.dao.DeleteZzalPort;
import com.chzzkzzal.zzal.domain.dao.LoadZzalPort;
import com.chzzkzzal.zzal.domain.dao.SaveZzalPort;
import com.chzzkzzal.zzal.domain.model.Zzal;

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
		return null;
	}

	@Override
	public Optional<Zzal> findById(Long id) {
		return Optional.empty();
	}

	@Override
	public void save(Zzal post) {

	}
}
