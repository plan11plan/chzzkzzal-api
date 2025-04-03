package com.chzzkzzal.core.s3;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.core.s3.service.S3ServicePort;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class S3Controller {

	private final S3ServicePort s3ServicePort;

	@PostMapping
	public ResponseEntity<List<String>> uploadFile(@RequestParam("files") List<MultipartFile> multipartFiles) {
		return ResponseEntity.ok(s3ServicePort.uploadFiles(multipartFiles));
	}

	@DeleteMapping
	public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
		s3ServicePort.deleteFile(fileName);
		return ResponseEntity.ok(fileName);
	}

	@GetMapping
	public ResponseEntity<String> downloadFile(@RequestParam(value= "fileName") String fileName) {
		String fileUrl = s3ServicePort.getFileUrl(fileName);
		return ResponseEntity.ok(fileUrl);
	}

}
