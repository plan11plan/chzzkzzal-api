package com.chzzkzzal.zzal.business.application.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MonitorConfig {
	@Bean
	public CountedAspect countedAspect(MeterRegistry registry) {
		return new CountedAspect(registry);
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}
}
