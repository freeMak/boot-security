package com.boot.security.server.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobModel extends BaseEntity<Long> {

	private static final long serialVersionUID = -2458935535811207209L;

	private String jobName;

	private String description;

	private String cron;

	private String springBeanName;

	private String methodName;

	private Boolean isSysJob;

	private int status;

}
