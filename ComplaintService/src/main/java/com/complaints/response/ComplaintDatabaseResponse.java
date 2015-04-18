package com.complaints.response;

import com.complaints.entities.ComplaintEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class ComplaintDatabaseResponse extends DatabaseResponse {
	
	@Getter @Setter
	private ComplaintEntity complaint;
	
}
