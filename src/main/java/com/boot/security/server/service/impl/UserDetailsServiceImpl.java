package com.boot.security.server.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info(username);
		Set<GrantedAuthority> authorities = new HashSet<>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("rrr");
		authorities.add(grantedAuthority);

		User user = new User(username, username, authorities);
		return user;
	}

}
