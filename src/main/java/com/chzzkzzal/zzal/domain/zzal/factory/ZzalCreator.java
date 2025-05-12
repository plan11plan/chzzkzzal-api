package com.chzzkzzal.zzal.domain.zzal.factory;

import com.chzzkzzal.zzal.application.port.in.command.SaveZzalCommand;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;
import com.chzzkzzal.zzal.domain.zzal.Zzal;

// ZzalCreator.java
public interface ZzalCreator {
	boolean supports(MediaMeta metadata);

	Zzal createZzal(SaveZzalCommand saveZzalCommand);
}

