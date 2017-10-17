package com.boot.security.server.dto;

import java.io.Serializable;

/**
 * Restful方式登陆token
 * 
 * @author 小威老师
 *
 *         2017年8月4日
 */
public class Token implements Serializable {

	private static final long serialVersionUID = 6314027741784310221L;

	private String token;

	public Token(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
