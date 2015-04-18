package com.complaints.response;

import lombok.Data;

@Data
public class ServiceResponse {

	private int status;
	
	private DatabaseResponse dbResp;
	
	private String message;
}
