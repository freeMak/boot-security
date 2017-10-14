package com.boot.security.server.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Restful方式登陆token
 * 
 * @author 小威老师
 *
 *         2017年8月4日
 */
@Getter
@Setter
@Builder
public class Token implements Serializable {

	private static final long serialVersionUID = 6314027741784310221L;

	private String token;

}
