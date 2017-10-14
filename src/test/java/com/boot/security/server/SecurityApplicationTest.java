package com.boot.security.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityApplicationTest {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	public void password() {
		String string = passwordEncoder.encode("admin");
		System.out.println(string);
		System.out.println(string.length());
	}
}
