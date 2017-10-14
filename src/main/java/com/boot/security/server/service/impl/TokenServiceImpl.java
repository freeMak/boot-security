package com.boot.security.server.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.boot.security.server.dto.LoginUser;
import com.boot.security.server.dto.Token;
import com.boot.security.server.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {

	/**
	 * token过期秒数
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;
	@Autowired
	private RedisTemplate<String, LoginUser> redisTemplate;

	@Override
	public Token saveToken(LoginUser loginUser) {
		String token = UUID.randomUUID().toString();
		redisTemplate.boundValueOps(getKey(token)).set(loginUser, expireSeconds, TimeUnit.SECONDS);

		return Token.builder().token(token).build();
	}

	@Override
	public LoginUser getLoginUser(String token) {
		return redisTemplate.boundValueOps(getKey(token)).get();
	}

	@Override
	public boolean deleteToken(String token) {
		if (redisTemplate.hasKey(getKey(token))) {
			redisTemplate.delete(getKey(token));

			return true;
		}

		return false;
	}

	private String getKey(String token) {
		return "tokens:" + token;
	}

}
