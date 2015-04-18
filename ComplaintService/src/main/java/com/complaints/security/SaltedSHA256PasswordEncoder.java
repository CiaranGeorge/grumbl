package com.complaints.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.complaints.utils.AWSUtils;

public class SaltedSHA256PasswordEncoder implements PasswordEncoder {
	
	private static Logger LOG = Logger.getLogger(SaltedSHA256PasswordEncoder.class);

	private final String salt;

	private final MessageDigest digest;

	public SaltedSHA256PasswordEncoder(String salt) throws NoSuchAlgorithmException {
		LOG.info("SaltedSHA256PasswordEncoder");
		this.salt = salt;
		this.digest = MessageDigest.getInstance("SHA-256");
	}

	@Override
	public String encode(CharSequence rawPassword) {
		LOG.info("encode");
		String saltedPassword = rawPassword + this.salt;
		try {
			return new String(Hex.encode(this.digest.digest(saltedPassword.getBytes("UTF-8"))));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 not supported");
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		LOG.info("checking if sha256 matches");
		return this.encode(rawPassword).equals(encodedPassword);
	}

}
