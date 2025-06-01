package com.chzzkzzal.test;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.chzzkzzal.common.error.CustomResponse;
import com.chzzkzzal.core.auth.adapter.in.web.security.MemberUserDetails;
import com.chzzkzzal.zzal.business.application.dto.ClientInfo;
import com.chzzkzzal.zzal.business.application.port.in.GetZzalAllUseCase;
import com.chzzkzzal.zzal.business.application.port.in.query.GetZzalDetailQuery;
import com.chzzkzzal.zzal.business.application.port.in.query.result.ZzalDetailResponse;
import com.chzzkzzal.zzal.util.CursorRequest;
import com.chzzkzzal.zzal.util.CursorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "짤 API", description = "")
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/zzals")
public class TestZzalController {

	private final TransactionGetZzalDetailService getZzalDetailUseCase;
	private final GetZzalAllUseCase getZzalAllUseCase;

	private void validateUser(MemberUserDetails userDetails) {
		if (userDetails == null || userDetails.getMember() == null) {
			// 필요하면 커스텀 예외 사용 가능
			// throw new UnAuthorizedException("인증 정보가 유효하지 않습니다.");s

			// 혹은 401/403을 직접 반환
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증 정보가 유효하지 않습니다.");
		}
	}

	@Operation(
		summary = "짤 단건 조회",
		description =
			"### 짤 단건 조회\n" +
				"- 짤 메타정보 포함 \n"
	)
	@GetMapping("{zzalId}")
	public ResponseEntity<CustomResponse<ZzalDetailResponse>> viewDetail(
		@PathVariable("zzalId") Long zzalId,
		HttpServletRequest request
	) {

		GetZzalDetailQuery query = new GetZzalDetailQuery(zzalId, ClientInfo.from(request));
		ZzalDetailResponse response = getZzalDetailUseCase.execute(query);
		return CustomResponse.okResponseEntity(response);
	}

	@Operation(
		summary = "짤 전체 조회",
		description =
			"### 짤 전체 조회\n" +
				"- findAll \n" +
				"### TODO \n" +
				"- 커서기반 리팩토링 \n"
	)
	@GetMapping
	public ResponseEntity<CustomResponse<List<ZzalDetailResponse>>> getAll() {
		List<ZzalDetailResponse> responses = getZzalAllUseCase.getAll();
		return CustomResponse.okResponseEntity(responses);
	}

	@GetMapping("/cursor")
	public ResponseEntity<CustomResponse<
		CursorResponse<ZzalDetailResponse>>> getAll(
		@RequestParam(required = false, defaultValue = "-1") Long key,
		@RequestParam(required = false, defaultValue = "20") int size
	) {
		CursorRequest cursorRequest = new CursorRequest(key, size);

		CursorResponse<ZzalDetailResponse> responses = getZzalAllUseCase.getAllByCursor(cursorRequest);
		return CustomResponse.okResponseEntity(responses);
	}
}
