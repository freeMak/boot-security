package com.boot.security.server.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.boot.security.server.dao.PermissionDao;
import com.boot.security.server.dao.UserDao;
import com.boot.security.server.dto.LoginUser;
import com.boot.security.server.dto.UserDto;
import com.boot.security.server.model.Permission;
import com.boot.security.server.model.SysUser;
import com.boot.security.server.model.SysUser.Status;
import com.boot.security.server.service.TokenService;
import com.boot.security.server.service.UserService;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "adminLogger")
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private TokenService tokenService;

	@Override
	@Transactional
	public SysUser saveUser(UserDto userDto) {
		SysUser user = userDto;
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setStatus(Status.VALID);
		userDao.save(user);
		saveUserRoles(user.getId(), userDto.getRoleIds());

		log.debug("新增用户{}", user.getUsername());
		return user;
	}

	private void saveUserRoles(Long userId, List<Long> roleIds) {
		if (roleIds != null) {
			userDao.deleteUserRole(userId);
			if (!CollectionUtils.isEmpty(roleIds)) {
				userDao.saveUserRoles(userId, roleIds);
			}
		}
	}

	@Override
	public SysUser getUser(String username) {
		return userDao.getUser(username);
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword) {
		SysUser u = userDao.getUser(username);
		if (u == null) {
			throw new IllegalArgumentException("用户不存在");
		}

		if (!passwordEncoder.matches(oldPassword, u.getPassword())) {
			throw new IllegalArgumentException("旧密码错误");
		}

		userDao.changePassword(u.getId(), passwordEncoder.encode(newPassword));

		log.debug("修改{}的密码", username);
	}

	@Override
	@Transactional
	public SysUser updateUser(UserDto userDto) {
		userDao.update(userDto);
		saveUserRoles(userDto.getId(), userDto.getRoleIds());
		updateLoginUserCache(Sets.newHashSet(userDto.getId()));

		return userDto;
	}

	/**
	 * 修改登陆用户的缓存
	 */
	@Override
	public void updateLoginUserCache(Set<Long> userIds) {
		if (CollectionUtils.isEmpty(userIds)) {
			return;
		}

		userIds.parallelStream().forEach(userId -> {
			String token = tokenService.getTokenByUserId(userId);
			if (!StringUtils.isEmpty(token)) {
				SysUser sysUser = userDao.getById(userId);

				LoginUser loginUser = new LoginUser();
				loginUser.setToken(token);
				BeanUtils.copyProperties(sysUser, loginUser);

				List<Permission> permissions = permissionDao.listByUserId(sysUser.getId());
				loginUser.setPermissions(permissions);

				tokenService.updateLoginUser(loginUser);
			}
		});
	}
}
