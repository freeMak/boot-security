package com.boot.security.server.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zw.admin.server.annotation.LogAnnotation;
import com.zw.admin.server.dao.UserDao;
import com.zw.admin.server.dto.UserDto;
import com.zw.admin.server.model.User;
import com.zw.admin.server.page.table.PageTableRequest;
import com.zw.admin.server.page.table.PageTableHandler;
import com.zw.admin.server.page.table.PageTableResponse;
import com.zw.admin.server.page.table.PageTableHandler.CountHandler;
import com.zw.admin.server.page.table.PageTableHandler.ListHandler;
import com.zw.admin.server.service.UserService;
import com.zw.admin.server.utils.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户相关接口
 * 
 * @author 小威老师
 *
 */
@Api(tags = "用户")
@Slf4j(topic = "adminLogger")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;

	@LogAnnotation
	@PostMapping
	@ApiOperation(value = "保存用户")
	@RequiresPermissions("sys:user:add")
	public User saveUser(@RequestBody UserDto userDto) {
		User u = userService.getUser(userDto.getUsername());
		if (u != null) {
			throw new IllegalArgumentException(userDto.getUsername() + "已存在");
		}

		return userService.saveUser(userDto);
	}

	@LogAnnotation
	@PutMapping
	@ApiOperation(value = "修改用户")
	@RequiresPermissions("sys:user:add")
	public User updateUser(@RequestBody UserDto userDto) {
		return userService.updateUser(userDto);
	}

	@LogAnnotation
	@PutMapping(params = "headImgUrl")
	@ApiOperation(value = "修改头像")
	public void updateHeadImgUrl(String headImgUrl) {
		User user = UserUtil.getCurrentUser();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		userDto.setHeadImgUrl(headImgUrl);

		userService.updateUser(userDto);
		log.debug("{}修改了头像", user.getUsername());
	}

	@LogAnnotation
	@PutMapping("/{username}")
	@ApiOperation(value = "修改密码")
	@RequiresPermissions("sys:user:password")
	public void changePassword(@PathVariable String username, String oldPassword, String newPassword) {
		userService.changePassword(username, oldPassword, newPassword);
	}

	@GetMapping
	@ApiOperation(value = "用户列表")
	@RequiresPermissions("sys:user:query")
	public PageTableResponse<User> listUsers(PageTableRequest request) {
		return PageTableHandler.<User> builder().countHandler(new CountHandler() {

			@Override
			public int count(PageTableRequest request) {
				return userDao.count(request.getParams());
			}
		}).listHandler(new ListHandler<User>() {

			@Override
			public List<User> list(PageTableRequest request) {
				List<User> list = userDao.list(request.getParams(), request.getOffset(), request.getLimit());
				return list;
			}
		}).build().handle(request);
	}

	@ApiOperation(value = "当前登录用户")
	@GetMapping("/current")
	public User currentUser() {
		return UserUtil.getCurrentUser();
	}

	@ApiOperation(value = "根据用户id获取用户")
	@GetMapping("/{id}")
	@RequiresPermissions("sys:user:query")
	public User user(@PathVariable Long id) {
		return userDao.getById(id);
	}

}
