package com.complaints.constants;

/**
 * Statuses:
 * N - New grumbl created
 * P - Pending response from customer
 * A - Additional details required by client
 * R - Responded to by customer
 * B - Complaint has been blocked
 */

public enum UserStatus {
	NEW('N'), PENDING_RESPONSE('P'), ADDITIONAL_DETAILS('A'), CLIENT_RESPONSE('R'), BLOCKED('B');
 
	private char statusCode;
 
	private UserStatus(char s) {
		statusCode = s;
	}
 
	public char getStatusCode() {
		return statusCode;
	}
 
}