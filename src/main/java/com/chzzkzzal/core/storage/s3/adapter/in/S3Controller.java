package com.chzzkzzal.core.storage.s3.adapter.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "S3 API", description = "")
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class S3Controller {

	private final S3Facade facade;

	@Operation(summary = "S3 파일 업로드", description = "S3 파일 업로드 API [담당자 : 김진수]")
	@PostMapping
	public ResponseEntity<List<String>> upload(@RequestParam("files") List<MultipartFile> files) {
		return ResponseEntity.ok(facade.uploadFiles(files));
	}

	@Operation(summary = "S3 파일 제거", description = "S3 파일 제거 API [담당자 : 김진수]")
	@DeleteMapping
	public ResponseEntity<Void> delete(@RequestParam String key) {
		facade.deleteFile(key);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "S3 파일 url 다운로드", description = "S3 파일 url 다운로드 API [담당자 : 김진수]")
	@GetMapping
	public ResponseEntity<String> url(@RequestParam String key) {
		return ResponseEntity.ok(facade.getFileUrl(key));
	}

}
