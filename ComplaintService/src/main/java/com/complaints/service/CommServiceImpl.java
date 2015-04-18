package com.complaints.service;

import com.complaints.entities.UserEntity;
import com.complaints.notification.MessageType;
import com.complaints.notification.NotificationService;
import com.complaints.notification.email.Email;
import com.complaints.notification.email.EmailBuilder;
import com.complaints.notification.email.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.mail.Message;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Communication Service Impl; For handling all Communication Service Requests
 */
public class CommServiceImpl implements CommService
{
    @Autowired
    @Qualifier("emailNotificationService")
    NotificationService notificationService;

    @Autowired
    EmailBuilder emailBuilder;

    @Override
    public boolean sendPasswordResetNotification(UserEntity userEntity,String newPassword)
    {
        Recipient recipient = new Recipient(userEntity.getUsername(), userEntity.getEmail(), Message.RecipientType.TO);
        final Email email = emailBuilder.generatePasswordResetEmail(recipient, newPassword);

        return notificationService.sendMessages(new ArrayList<MessageType>() {{
            add(email);
        }});
    }

    @Override
    public boolean sendWelcomeNotification(List<UserEntity> userEntities)
    {
        List<MessageType> welcomeEmails= new ArrayList<>();

        for(UserEntity user : userEntities){
            welcomeEmails.add(emailBuilder.generateWelcomeEmail(
                    new Recipient(user.getUsername(), user.getEmail(), Message.RecipientType.TO)
            ));
        }

        return notificationService.sendMessages(welcomeEmails);
    }
    
    @Override
	public boolean sendUsernameReminderNotification(UserEntity user) {
    	String name = user.getFirstname()+" "+user.getLastname();
    	Recipient recipient = new Recipient(name, user.getEmail(), Message.RecipientType.TO);
        final Email email = emailBuilder.generateUsernameReminderEmail(recipient,user.getUsername());

        return notificationService.sendMessages(new ArrayList<MessageType>() {{
            add(email);
        }});
    }
}
