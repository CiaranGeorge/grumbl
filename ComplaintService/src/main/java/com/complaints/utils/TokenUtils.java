package com.complaints.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import com.complaints.dao.UserDAOImpl;


public class TokenUtils {

	private static Logger LOG = Logger.getLogger(TokenUtils.class);
	
	public static final String MAGIC_KEY = "###";

	/**
     * @author c.george
     * 
     * Create a token for authentication purposes.
     */
	public static String createToken(UserDetails userDetails) {
		LOG.info("createToken");
		/* Expires in one hour */
		long expires = System.currentTimeMillis() + 1000L * 60 * 60;

		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(userDetails.getUsername());
		tokenBuilder.append(":");
		tokenBuilder.append(expires);
		tokenBuilder.append(":");
		tokenBuilder.append(TokenUtils.computeSignature(userDetails, expires));

		return tokenBuilder.toString();
	}

	/**
     * @author c.george
     * 
     * Generate token signature based on user details and expiry.
     */
	public static String computeSignature(UserDetails userDetails, long expires) {
		LOG.info("computeSignature");
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(userDetails.getUsername());
		signatureBuilder.append(":");
		signatureBuilder.append(expires);
		signatureBuilder.append(":");
		signatureBuilder.append(userDetails.getPassword());
		signatureBuilder.append(":");
		signatureBuilder.append(TokenUtils.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}

	/**
     * @author c.george
     * 
     * Extract username from the start of the token.
     */
	public static String getUserNameFromToken(String authToken) {
		LOG.info("getUserNameFromToken");
		if (null == authToken) {
			return null;
		}

		String[] parts = authToken.split(":");
		return parts[0];
	}

	/**
     * @author c.george
     * 
     * Ensure the token has not expired.
     */
	public static boolean validateToken(String authToken, UserDetails userDetails) {
		LOG.info("validateToken");
		String[] parts = authToken.split(":");
		long expires = Long.parseLong(parts[1]);
		String signature = parts[2];

		if (expires < System.currentTimeMillis()) {
			return false;
		}

		return signature.equals(TokenUtils.computeSignature(userDetails, expires));
	}
}
