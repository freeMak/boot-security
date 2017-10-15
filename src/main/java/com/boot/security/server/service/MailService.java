package com.boot.security.server.service;

import java.util.List;

import com.boot.security.server.model.Mail;

public interface MailService {

	void save(Mail mail, List<String> toUser);
}
