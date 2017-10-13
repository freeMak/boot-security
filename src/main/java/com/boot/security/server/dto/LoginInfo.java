package com.boot.security.server.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 4680419308278241260L;

	private String username;
	private String password;
}
