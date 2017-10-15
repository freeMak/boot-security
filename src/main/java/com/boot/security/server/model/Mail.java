package com.boot.security.server.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail extends BaseEntity<Long> {

	private static final long serialVersionUID = 5613231124043303948L;

	private Long userId;
	private String toUsers;
	private String subject;
	private String content;

}
