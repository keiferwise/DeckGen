package com.kif.deckgenmodels;

import java.math.BigInteger;

public class User {
	
	
	private String userId;
	
	private String userName;
	
	private String email;
	
	private String name;
		
	private boolean admin;
	
	private boolean enabled;
	
	private BigInteger tokens;
	
	private String role;
	
	private String password;
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", firstName=" + name
				+ ", lastName=" + name + ", admin=" + admin + ", password=" + password + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return name;
	}

	public void setFirstName(String firstName) {
		this.name = firstName;
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public BigInteger getTokens() {
		return tokens;
	}

	public void setTokens(BigInteger tokens) {
		this.tokens = tokens;
	}
	
}
