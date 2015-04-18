package com.complaints.notification.email;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.complaints.persistence.HibernateUtil;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for generating the different email types
 */
@Component
public class EmailBuilder
{
	
	private static Logger LOG = Logger.getLogger(HibernateUtil.class);
	
    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * Generate welcome email from velocity templates
     * @param recipient single recipient; as users information is injected
     * @return Generated Email Message
     */
    public Email generateWelcomeEmail(final Recipient recipient)
    {
    	LOG.info("generateWelcomeEmail");
        VelocityContext context = new VelocityContext();
        context.put("username", recipient.getName());
        String body = injectDataIntoTemplate("emails/welcome.vm", context);

        return new Email("Welcome Email", body, new  ArrayList<Recipient>() {{
            add(recipient);
        }} );
    }

    /**
     * Generate password reset email from velocity template
     * @param recipient single recipient; as users information is injected
     * @param newPassword 
     * @return Generated Email Message
     */
    public Email generatePasswordResetEmail(final Recipient recipient, String newPassword)
    {
    	LOG.info("generatePasswordResetEmail");
        VelocityContext context = new VelocityContext();
        context.put("username", recipient.getName());
        context.put("password", newPassword);
        String body = injectDataIntoTemplate("emails/pwreset.vm", context);

        return new Email("Password Reset Email", body, new  ArrayList<Recipient>() {{
            add(recipient);
        }} );
    }
    
	public Email generateUsernameReminderEmail(final Recipient recipient, String username) {
		LOG.info("generateUsernameReminderEmail");
		VelocityContext context = new VelocityContext();
        context.put("name", recipient.getName());
        context.put("username", username);
        String body = injectDataIntoTemplate("emails/usernameReminder.vm", context);

        return new Email("Username Reminder Email", body, new  ArrayList<Recipient>() {{
            add(recipient);
        }} );
	}

    /**
     * Generate newsletter email from velocity template
     * @param recipients
     * @return
     */
    public Email generateGlobalNewsLetter(List<Recipient> recipients){
    	LOG.info("generateGlobalNewsLetter");
        return null;
    }

    /**
     * Inject the contect map into the specified email template
     * @param velocityContext
     * @return
     */
    private String injectDataIntoTemplate(String emailTemplate, VelocityContext velocityContext)
    {
    	LOG.info("injectDataIntoTemplate");
        Template t = velocityEngine.getTemplate(emailTemplate);
        StringWriter writer = new StringWriter();
        t.merge(velocityContext , writer);
        return writer.toString();
    }

}
