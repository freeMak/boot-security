package com.boot.security.server.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeanField implements Serializable {

	private static final long serialVersionUID = 4279960350136806659L;

	private String columnName;

	private String columnType;

	private String columnComment;

	private String columnDefault;

	private String name;

	private String type;
}
