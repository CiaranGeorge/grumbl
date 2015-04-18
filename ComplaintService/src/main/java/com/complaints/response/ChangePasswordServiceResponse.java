package com.complaints.response;

import lombok.Getter;
import lombok.Setter;

public class ChangePasswordServiceResponse extends ServiceResponse{
	@Getter @Setter private String token;
}
