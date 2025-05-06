package com.chzzkzzal.core.auth.domain.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chzzkzzal.core.auth.domain.MemberUserDetails;
import com.chzzkzzal.core.auth.domain.repository.LoadMemberPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberUserDetailService implements UserDetailsService {
	private final LoadMemberPort loadMemberPort;

	@Override
	public MemberUserDetails loadUserByUsername(String channelId) throws UsernameNotFoundException {
		return loadMemberPort.findByChannelId(channelId)
			.map(MemberUserDetails::new)
			.orElseThrow(() -> new UsernameNotFoundException("Not found: " + channelId));
	}
}
