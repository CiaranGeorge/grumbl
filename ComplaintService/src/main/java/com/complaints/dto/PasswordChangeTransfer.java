package com.complaints.dto;

import lombok.Getter;
import lombok.Setter;

public class PasswordChangeTransfer {

	@Getter @Setter private String oldPassword;
	
	@Getter @Setter private String newPassword;
	
}
