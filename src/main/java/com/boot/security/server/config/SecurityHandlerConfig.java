package com.boot.security.server.config;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.alibaba.fastjson.JSONObject;
import com.boot.security.server.dto.ResponseInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityHandlerConfig {

	/**
	 * 登陆成功
	 * 
	 * @return
	 */
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new AuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {
				log.info(request.getRequestURI());
				User user = (User) authentication.getPrincipal();
				log.info("{}", user.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet()));

				ResponseInfo info = ResponseInfo.builder().code(HttpStatus.OK.value() + "").message("登录成功").build();

				writeResponse(response, HttpStatus.OK.value(), JSONObject.toJSONString(info));
			}
		};
	}

	/**
	 * 登陆失败
	 * 
	 * @return
	 */
	@Bean
	public AuthenticationFailureHandler loginFailureHandler() {
		return new AuthenticationFailureHandler() {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				ResponseInfo info = ResponseInfo.builder().code(HttpStatus.UNAUTHORIZED.value() + "")
						.message(exception.getMessage()).build();
				writeResponse(response, HttpStatus.UNAUTHORIZED.value(), JSONObject.toJSONString(info));
			}
		};

	}

	/**
	 * 退出处理
	 * 
	 * @return
	 */
	@Bean
	public LogoutSuccessHandler logoutSussHandler() {
		return new LogoutSuccessHandler() {

			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {

			}
		};

	}

	public static void writeResponse(HttpServletResponse response, int status, String json) {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "*");
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(status);
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
