package com.chzzkzzal.zzal.adapter.in.event;

import org.springframework.context.ApplicationEventPublisher;

public class Events {
	private static ApplicationEventPublisher publisher;

	static void setPublisher(ApplicationEventPublisher publisher) {
		Events.publisher = publisher;
	}

	public static void raise(Object event) {
		if (publisher != null) {
			publisher.publishEvent(event);
		}
	}
}
