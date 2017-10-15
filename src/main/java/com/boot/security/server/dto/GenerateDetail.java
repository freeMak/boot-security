package com.boot.security.server.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateDetail implements Serializable {

	private static final long serialVersionUID = -164567294469931676L;

	private String beanName;

	private List<BeanField> fields;
}
