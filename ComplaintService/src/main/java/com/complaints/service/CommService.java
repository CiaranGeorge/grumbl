package com.complaints.service;

import com.complaints.entities.UserEntity;

import java.util.List;

public interface CommService {
    /**
     * Send password reset notification
     * @param userEntity User who has had a password reset request raised
     * @param newPassword 
     * @return
     */
    boolean sendPasswordResetNotification(UserEntity userEntity, String newPassword);

    /**
     * Send welcome notification
     * @param userEntities A list of users that will receive a welcome email
     * @return
     */
    boolean sendWelcomeNotification(List<UserEntity> userEntities);
    
    /**
     * Send username reminder
     * @param user
     * @return
     */
	boolean sendUsernameReminderNotification(UserEntity user);
}
