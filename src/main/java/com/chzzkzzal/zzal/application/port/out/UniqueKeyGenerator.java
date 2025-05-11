package com.chzzkzzal.zzal.application.port.out;

public interface UniqueKeyGenerator {
	String generate(String ipAddress, String userAgent);

}
