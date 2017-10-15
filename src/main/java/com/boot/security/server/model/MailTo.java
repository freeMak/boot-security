package com.boot.security.server.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailTo extends BaseEntity<Long> {

	private static final long serialVersionUID = -8238779033956731073L;

	private Long mailId;
	private String toUser;
	private Boolean status;
}
