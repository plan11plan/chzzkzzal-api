package com.chzzkzzal.zzal.util;

import java.util.List;

public record CursorResponse<T>(
	CursorRequest nextCursorRequest,
	List<T> contents

) {

}
