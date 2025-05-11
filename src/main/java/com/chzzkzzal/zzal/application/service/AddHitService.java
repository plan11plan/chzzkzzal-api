package com.chzzkzzal.zzal.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chzzkzzal.zzal.application.port.in.AddHitUsecase;
import com.chzzkzzal.zzal.application.port.in.command.AddHitCommand;
import com.chzzkzzal.zzal.application.port.out.SaveHitPort;
import com.chzzkzzal.zzal.application.port.out.UniqueKeyGenerator;
import com.chzzkzzal.zzal.domain.zzal.ZzalHit;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AddHitService implements AddHitUsecase {

	private final SaveHitPort saveHitPort;
	private final UniqueKeyGenerator keyGen;

	@Override
	public String addHit(final AddHitCommand command) {
		String key = keyGen.generate(
			command.clientInfo().ipAddress(),
			command.clientInfo().userAgent()
		);
		ZzalHit zzalHit = ZzalHit.of(
			command.zzalId(),
			key,
			command.clientInfo()
		);

		saveHitPort.saveIgnoreDuplicate(zzalHit);
		return key;
	}

}
