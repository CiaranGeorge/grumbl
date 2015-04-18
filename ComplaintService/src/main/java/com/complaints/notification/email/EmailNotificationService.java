package com.complaints.notification.email;

import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.complaints.notification.MessageType;
import com.complaints.notification.NotificationService;
import com.complaints.notification.email.utils.EmailValidationUtil;

/**
 * Email Implementation of the Notification Service
 */
@Configuration
public class EmailNotificationService implements NotificationService
{
    private final static Logger LOG = Logger.getLogger(EmailNotificationService.class.getName());

    @Value("#{smtpProperties['mail.smtp.username']}")
    private String username;

    @Value("#{smtpProperties['mail.smtp.password']}")
    private String password;
    
    @Value("#{smtpProperties['mail.smtp.emailAddress']}")
    private String emailAddress;

    @Value("#{smtpProperties['mail.smtp.host']}")
    private String smtpHost;

    @Value("#{smtpProperties['mail.smtp.socketFactory.port']}")
    private String socketFactoryPort;

    @Value("#{smtpProperties['mail.smtp.socketFactory.class']}")
    private String socketFactoryClass;

    @Value("#{smtpProperties['mail.smtp.auth']}")
    private String smtpAuth;

    @Value("#{smtpProperties['mail.smtp.port']}")
    private String smtpPort;

    @Override
    public boolean sendMessages(List<MessageType> emailMessages)
    {
    	LOG.info("sendMessages");
        boolean wasEmailSent = false;
        for(MessageType emailMessage : emailMessages) {

            List<Recipient> recipientList = emailMessage.getRecipients();

            // Ensure that there is at least one recipient
            if (EmailValidationUtil.validateRecipientCollection(recipientList)) {
                Properties props = new Properties();
                props.put("mail.smtp.host", this.smtpHost);
                props.put("mail.smtp.socketFactory.port", this.socketFactoryPort);
                props.put("mail.smtp.socketFactory.class", this.socketFactoryClass);
                props.put("mail.smtp.auth", this.smtpAuth);
                props.put("mail.smtp.port", this.smtpPort);

                Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        }
                );

                try {
                    Message email = new MimeMessage(session);
                    email.setFrom(new InternetAddress(emailAddress));
                    for (Recipient recipient : recipientList) {
                        email.addRecipient(recipient.getType(), new InternetAddress(recipient.getAddress()));
                        System.out.println("Adding " + recipient.getAddress() + " as a recipient");
                    }

                    email.setSubject(emailMessage.getSubject());
                    email.setContent(emailMessage.getContent(), "text/html; charset=utf-8");

                    Transport.send(email);
                    
                    LOG.info("email sent");
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return wasEmailSent;
    }
}