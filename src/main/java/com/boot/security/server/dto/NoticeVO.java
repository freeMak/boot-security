package com.boot.security.server.dto;

import java.io.Serializable;
import java.util.List;

import com.boot.security.server.model.Notice;
import com.boot.security.server.model.SysUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeVO implements Serializable {

	private static final long serialVersionUID = 7363353918096951799L;

	private Notice notice;

	private List<SysUser> users;
}
