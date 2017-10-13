package com.boot.security.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**", "/layui/**", "/files/*", "/v2/api-docs/**",
						"/swagger-resources/**", "/swagger-ui.html", "/webjars/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginProcessingUrl("/login")
				.successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler).and()
				.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
	}

}
