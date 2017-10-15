package com.boot.security.server.service;

import com.zw.admin.server.dto.UserDto;
import com.zw.admin.server.model.User;

public interface UserService {

	User saveUser(UserDto userDto);
	
	User updateUser(UserDto userDto);

	String passwordEncoder(String credentials, String salt);

	User getUser(String username);

	void changePassword(String username, String oldPassword, String newPassword);

}
