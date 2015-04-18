package com.complaints.utils;

import java.util.Random;
import org.apache.log4j.Logger;

public class PasswordUtils
{
	private static Logger LOG = Logger.getLogger(PasswordUtils.class);
	
    // Prevent init of utility class
    private PasswordUtils(){}

    /**
     * Generate random token
     * @return
     */
    public static String generateRandomPassword()
    {
    	LOG.info("generateRandomPassword");
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        };
        return sb.toString();
    }
}
