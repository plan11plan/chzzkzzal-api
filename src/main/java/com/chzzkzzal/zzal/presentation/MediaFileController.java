package com.chzzkzzal.zzal.presentation;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chzzkzzal.zzal.application.port.in.query.ExtractMetadataQuery;
import com.chzzkzzal.zzal.application.service.GetMetadataService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "MediaFileController", description = "테스트용")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mediaFile")
public class MediaFileController {
	private final GetMetadataService getMetadataService;

	@PostMapping("tt")
	public Object getMetadata(@RequestParam("files") MultipartFile multipartFiles) {
		try {
			return getMetadataService.getMetadata(
				new ExtractMetadataQuery(
					multipartFiles.getBytes(),
					multipartFiles.getOriginalFilename(),
					multipartFiles.getContentType())
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
