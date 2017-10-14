package com.boot.security.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.boot.security.server.service.impl.UserDetailsServiceImpl;

@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/login.html", "/statics/**", "/v2/api-docs/**", "/swagger-resources/**",
						"/swagger-ui.html", "/webjars/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login.html")
				.loginProcessingUrl("/login").successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler).and().logout().logoutUrl("/logout")
				.logoutSuccessHandler(logoutSuccessHandler).and().csrf().disable().headers().frameOptions()
				.sameOrigin();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new PasswordEncoder() {

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.equals(encodedPassword);
			}

			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}
		});
	}

}
