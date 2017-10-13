package com.boot.security.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.security.server.dto.LoginInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 登陆相关接口
 * 
 * @author 小威老师
 *
 */
@Api(tags = "登陆、退出")
@RestController
@RequestMapping
public class LoginController {

	@ApiOperation(value = "登陆")
	@PostMapping("/login")
	public void login(@RequestBody LoginInfo info) {
		System.out.println(info.getUsername());
		System.out.println(info.getPassword());
	}

	@ApiOperation(value = "退出")
	@GetMapping(value = "/logout", params = "token")
	public void logout(String token) {
		System.out.println(token);
	}

}
