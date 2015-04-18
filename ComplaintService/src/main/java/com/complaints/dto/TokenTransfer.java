package com.complaints.dto;

public class TokenTransfer {

	private String token;
	
	private String message;

	public TokenTransfer(String token, String message) {

		this.token = token;
		this.message = message;
	}
	
	public TokenTransfer(String message) {
		this.message = message;
	}

	public String getToken() {
		return this.token;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
