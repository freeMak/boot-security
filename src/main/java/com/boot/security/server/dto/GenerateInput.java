package com.boot.security.server.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateInput implements Serializable {

	private static final long serialVersionUID = -2870071259702969061L;

	/**
	 * 保存路径
	 */
	private String path;

	private String tableName;

	/**
	 * bean包名
	 */
	private String beanPackageName;

	/**
	 * java类名
	 */
	private String beanName;
	/**
	 * dao包名
	 */
	private String daoPackageName;

	/**
	 * dao类名
	 */
	private String daoName;
	/**
	 * controller包名
	 */
	private String controllerPkgName;
	/**
	 * controller类名
	 */
	private String controllerName;
	/**
	 * 字段名
	 */
	private List<String> columnNames;
	/**
	 * 属性名
	 */
	private List<String> beanFieldName;
	/**
	 * 成员变量类型
	 */
	private List<String> beanFieldType;
	/**
	 * 默认值
	 */
	private List<String> beanFieldValue;
}
