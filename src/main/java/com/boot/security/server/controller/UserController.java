package com.boot.security.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.security.server.annotation.LogAnnotation;
import com.boot.security.server.dao.UserDao;
import com.boot.security.server.dto.UserDto;
import com.boot.security.server.model.SysUser;
import com.boot.security.server.page.table.PageTableHandler;
import com.boot.security.server.page.table.PageTableHandler.CountHandler;
import com.boot.security.server.page.table.PageTableHandler.ListHandler;
import com.boot.security.server.page.table.PageTableRequest;
import com.boot.security.server.page.table.PageTableResponse;
import com.boot.security.server.service.UserService;
import com.boot.security.server.utils.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户相关接口
 * 
 * @author 小威老师
 *
 */
@Api(tags = "用户")

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存用户")
	@PreAuthorize("hasAuthority('sys:user:add')")
	public SysUser saveUser(@RequestBody UserDto userDto) {
		SysUser u = userService.getUser(userDto.getUsername());
		if (u != null) {
			throw new IllegalArgumentException(userDto.getUsername() + "已存在");
		}

		return userService.saveUser(userDto);
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改用户")
	@PreAuthorize("hasAuthority('sys:user:add')")
	public SysUser updateUser(@RequestBody UserDto userDto) {
		return userService.updateUser(userDto);
	}

	@LogAnnotation
	@PutMapping(params = "headImgUrl")
	@ApiOperation(value = "修改头像")
	public void updateHeadImgUrl(String headImgUrl) {
		SysUser user = UserUtil.getLoginUser();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		userDto.setHeadImgUrl(headImgUrl);

		userService.updateUser(userDto);
		log.debug("{}修改了头像", user.getUsername());
	}

	@LogAnnotation
	@PutMapping("/{username}")
	@ApiOperation(value = "修改密码")
	@PreAuthorize("hasAuthority('sys:user:password')")
	public void changePassword(@PathVariable String username, String oldPassword, String newPassword) {
		userService.changePassword(username, oldPassword, newPassword);
	}

	@GetMapping
	@ApiOperation(value = "用户列表")
	@PreAuthorize("hasAuthority('sys:user:query')")
	public PageTableResponse listUsers(PageTableRequest request) {
		return new PageTableHandler(new CountHandler() {

			@Override
			public int count(PageTableRequest request) {
				return userDao.count(request.getParams());
			}
		}, new ListHandler() {

			@Override
			public List<SysUser> list(PageTableRequest request) {
				List<SysUser> list = userDao.list(request.getParams(), request.getOffset(), request.getLimit());
				return list;
			}
		}).handle(request);
	}

	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public SysUser currentUser() {
		return UserUtil.getLoginUser();
	}

	@ApiOperation(value = "根据用户id获取用户")
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('sys:user:query')")
	public SysUser user(@PathVariable Long id) {
		return userDao.getById(id);
	}

}
