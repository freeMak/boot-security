package com.boot.security.server.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LayuiFile implements Serializable {

	private static final long serialVersionUID = 35435494737590569L;

	private Integer code;
	private String msg;
	private LayuiFileData data;

	@Getter
	@Setter
	public static class LayuiFileData implements Serializable {

		private static final long serialVersionUID = 7907356434695924597L;
		private String src;
		private String title;
	}
}
