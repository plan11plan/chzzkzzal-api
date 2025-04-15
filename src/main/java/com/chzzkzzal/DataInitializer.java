package com.chzzkzzal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.chzzkzzal.streamer.RegisterStreamerCommand;
import com.chzzkzzal.streamer.StreamerService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

	private final StreamerService streamerService;

	// application.yml에 설정한 값
	@Value("${chzzkzzal.domain}")
	private String domain;

	@Override
	public void run(String... args) throws Exception {
		도파();
		랄로();
		파카();
		괴물쥐();
	}

	private void 괴물쥐() {
		RegisterStreamerCommand command = new RegisterStreamerCommand(
			"c7ded8ea6b0...",
			"괴물쥐",
			domain + "/zzal/괴물쥐.jpg", // <-- 이렇게 외부에서 주입한 값을 사용
			290000
		);
		streamerService.register(command);
	}

	// 이하 동일
	private void 도파() {
		RegisterStreamerCommand command = new RegisterStreamerCommand(
			"c847a5....",
			"도파99",
			domain + "/zzal/도파.jpg",
			114000
		);
		streamerService.register(command);
	}

	private void 랄로() {
		RegisterStreamerCommand command = new RegisterStreamerCommand(
			"3497a9....",
			"랄로",
			domain + "/zzal/랄로.png",
			315000
		);
		streamerService.register(command);
	}

	private void 파카() {
		RegisterStreamerCommand command = new RegisterStreamerCommand(
			"42597020c1a7...",
			"파카",
			domain + "/zzal/파카.jpg",
			224000
		);
		streamerService.register(command);
	}
}
