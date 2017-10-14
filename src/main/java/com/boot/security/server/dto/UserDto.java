package com.boot.security.server.dto;

import java.util.List;

import com.boot.security.server.model.SysUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends SysUser {

	private static final long serialVersionUID = -184009306207076712L;

	private List<Long> roleIds;

}
