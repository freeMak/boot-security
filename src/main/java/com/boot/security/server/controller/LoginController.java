package com.boot.security.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 登陆相关接口
 * 
 * @author 小威老师
 *
 */
@Slf4j
@Api(tags = "退出")
@RestController
@RequestMapping
public class LoginController {

	@ApiOperation(value = "退出")
	@GetMapping(value = "/logout", params = "token")
	public void logout(String token) {
		log.info(token);
	}

}
