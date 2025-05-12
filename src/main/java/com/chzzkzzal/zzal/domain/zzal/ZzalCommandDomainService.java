package com.chzzkzzal.zzal.domain.zzal;

import org.springframework.stereotype.Service;

import com.chzzkzzal.zzal.application.port.in.command.SaveZzalCommand;
import com.chzzkzzal.zzal.application.port.out.SaveZzalPort;
import com.chzzkzzal.zzal.application.service.ZzalCreatorResolver;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZzalCommandDomainService {
	private final SaveZzalPort saveZzalPort;
	private final ZzalCreatorResolver zzalCreatorResolver;

	public Long save(SaveZzalCommand command) {
		Zzal zzal = zzalCreatorResolver
			.getFactory(command.mediaMeta())
			.createZzal(command);
		
		return saveZzalPort
			.save(zzal)
			.getId();
	}
}
