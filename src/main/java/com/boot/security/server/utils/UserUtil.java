package com.boot.security.server.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.boot.security.server.dto.LoginUser;

public class UserUtil {

	public static LoginUser getLoginUser() {
		LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return loginUser;
	}

}
