package com.chp.BookStore.dto;

import com.chp.BookStore.enums.UserRole;

public class AuthenticationResponse {


	private String jwt;
	
	private UserRole userRole;
	
	private long userId;
	
	

	public AuthenticationResponse(String jwt, UserRole userRole, long userId) {
		super();
		this.jwt = jwt;
		this.userRole = userRole;
		this.userId = userId;
	}

	public AuthenticationResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
