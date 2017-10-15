package com.boot.security.server.utils;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.security.core.context.SecurityContextHolder;

import com.boot.security.server.dto.LoginUser;
import com.boot.security.server.model.SysUser;
import com.zw.admin.server.constants.UserConstants;
import com.zw.admin.server.model.Permission;
import com.zw.admin.server.model.User;

public class UserUtil {

	public static LoginUser getLoginUser() {
		LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return loginUser;
	}

	public static void setUserSession(User user) {
		getSession().setAttribute(UserConstants.LOGIN_USER, user);
	}

	@SuppressWarnings("unchecked")
	public static List<Permission> getCurrentPermissions() {
		List<Permission> list = (List<Permission>) getSession().getAttribute(UserConstants.USER_PERMISSIONS);

		return list;
	}

	public static void setPermissionSession(List<Permission> list) {
		getSession().setAttribute(UserConstants.USER_PERMISSIONS, list);
	}

}
