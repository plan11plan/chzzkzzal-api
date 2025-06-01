package com.chzzkzzal.zzal.util;

public record CursorRequest(Long key, int size) {
	public static final Long NONE_KEY = -1L;

	public CursorRequest next(Long key) {
		return new CursorRequest(key, size);
	}

	public boolean hasKey() {
		return key != null && !key.equals(NONE_KEY);
	}
}
