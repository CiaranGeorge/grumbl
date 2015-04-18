package com.complaints.notification.email.utils;

import com.complaints.notification.email.Recipient;
import com.complaints.persistence.HibernateUtil;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class EmailValidationUtil
{
	
	private static Logger LOG = Logger.getLogger(HibernateUtil.class);
	
    private static Pattern pattern;
    private static Matcher matcher;
    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Private constructor; To prevent instantiation of the utility class
     */
    private EmailValidationUtil() {
        //
    }

    /**
     * Ensure that a Recipient list is not empty
     * @param recipients A list of recipients for an email
     * @return boolean indicating if a list is empty or not
     */
    public static boolean validateRecipientCollection(Collection<Recipient> recipients){
        return recipients.size() > 0 ? true : false;
    }

    /**
     * Validate that an email address is of correct format
     * @param email email to validate
     * @return
     */
    public static boolean validateEmail(String email)
    {
    	LOG.info("validateEmail");
        pattern = pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}