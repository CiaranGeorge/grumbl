package com.complaints.notification.email;

import lombok.Getter;
import javax.mail.Message.RecipientType;

/**
 * A Recipient of an email notification
 */
public class Recipient
{
    @Getter private final String name;
    @Getter private final String address;
    @Getter private final RecipientType type;

    public Recipient(final String name, final String address, final RecipientType type){
        this.name = name;
        this.address = address;
        this.type = type;
    }
}
