package com.chzzkzzal.zzalhits.service;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.application.port.in.command.AddHitCommand;
import com.chzzkzzal.zzal.application.port.in.query.ClientInfo;
import com.chzzkzzal.zzalhits.domain.ZzalHits;
import com.chzzkzzal.zzalhits.repository.ZzalHitsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalHitsService {
	private final ZzalHitsRepository zzalHitsRepository;

	public ZzalHits addHits(AddHitCommand command) {
		ZzalHits zzalHits = ZzalHits.addFromRequest(command.zzalId(), command.clientInfo());

		boolean exists = zzalHitsRepository.existsByUniqueIdentifier(zzalHits.getUniqueIdentifier());
		if (!exists) {
			zzalHitsRepository.save(zzalHits);
		}
		return zzalHits;
	}

	public void insert(Long zzalId, ClientInfo clientInfo) {
		ZzalHits zzalHits = ZzalHits.addFromRequest(zzalId, clientInfo);

		zzalHitsRepository.save(zzalHits);

	}

	public Long count(Long zzalId) {

		Long aLong = zzalHitsRepository.countByZzalId(zzalId);
		return aLong;
	}
}
