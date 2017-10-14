package com.boot.security.server.dto;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.boot.security.server.model.SysUser;

public class LoginUser extends SysUser implements UserDetails {

	private static final long serialVersionUID = 1422037805178348848L;

	private Set<GrantedAuthority> authorities;

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	// 账户是否未过期
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 账户是否未锁定
	@Override
	public boolean isAccountNonLocked() {
		return getStatus() != Status.LOCKED;
	}

	// 密码是否未过期
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 账户是否激活
	@Override
	public boolean isEnabled() {
		return true;
	}

}
