package com.complaints.notification;

import java.util.List;

/**
 * Interface to support multiple notification services
 * e.g. Email, Text
 */
public interface NotificationService {
    boolean sendMessages(List<MessageType> messages);
}
