package com.boot.security.server.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.boot.security.server.dao.TokenDao;
import com.boot.security.server.dto.LoginUser;
import com.boot.security.server.dto.Token;
import com.boot.security.server.model.TokenModel;
import com.boot.security.server.service.SysLogService;
import com.boot.security.server.service.TokenService;

/**
 * token存到数据库的实现类
 * 
 * @author 小威老师
 *
 */
@Service
public class TokenServiceDbImpl implements TokenService {

	/**
	 * token过期秒数
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;
	@Autowired
	private TokenDao tokenDao;
	@Autowired
	private SysLogService logService;

	@Override
	public Token saveToken(LoginUser loginUser) {
		String token = UUID.randomUUID().toString();

		loginUser.setToken(token);
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);

		TokenModel model = new TokenModel();
		model.setId(token);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
		model.setExpireTime(new Date(loginUser.getExpireTime()));
		model.setVal(JSONObject.toJSONString(loginUser));

		tokenDao.save(model);
		// 登陆日志
		logService.save(loginUser.getId(), "登陆", true, null);

		return new Token(token, loginUser.getLoginTime());
	}

	@Override
	public void refresh(LoginUser loginUser) {
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);

		TokenModel model = tokenDao.getById(loginUser.getToken());
		model.setUpdateTime(new Date());
		model.setExpireTime(new Date(loginUser.getExpireTime()));
		model.setVal(JSONObject.toJSONString(loginUser));

		tokenDao.update(model);
	}

	@Override
	public LoginUser getLoginUser(String token) {
		TokenModel model = tokenDao.getById(token);
		return toLoginUser(model);
	}

	@Override
	public boolean deleteToken(String token) {
		TokenModel model = tokenDao.getById(token);
		LoginUser loginUser = toLoginUser(model);
		if (loginUser != null) {
			tokenDao.delete(token);
			logService.save(loginUser.getId(), "退出", true, null);

			return true;
		}

		return false;
	}

	private LoginUser toLoginUser(TokenModel model) {
		if (model == null) {
			return null;
		}
		return JSONObject.parseObject(model.getVal(), LoginUser.class);
	}

}
