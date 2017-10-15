package com.boot.security.server.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfo extends BaseEntity<String> {

	private static final long serialVersionUID = -5761547882766615438L;

	private String contentType;
	private long size;
	private String path;
	private String url;
	private Integer type;
}
