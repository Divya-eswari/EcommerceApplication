package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {

	@JsonProperty
	private String username;


	//I am adding a new arguments to make user request with proper authentication and authorization

	//HERE, I am defining password and confirm password to provide security
	@JsonProperty
	private String password;

	@JsonProperty
	private String confirmPassword;


	//getter and setter methods of above parameters

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
