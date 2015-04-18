package com.complaints.notification;

import com.complaints.notification.email.Recipient;

import java.util.Collection;
import java.util.List;

/**
 * Message Type for the notification service
 */
public interface MessageType
{
    String getSubject();
    String getContent();
    List<Recipient> getRecipients();
}
