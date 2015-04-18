package com.complaints.notification.email;

import com.complaints.notification.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Custom Message POJO
 */
public class Email implements MessageType
{
    @Getter @Setter private String subject;
    @Getter @Setter private String content;
    @Getter @Setter private List<Recipient> recipients;

    public Email(String subject, String textHtml, List<Recipient> recipients){
        this.subject = subject;
        this.content = textHtml;
        this.recipients = recipients;
    }

    public void addRecipient (Recipient recipient){
        this.recipients.add(recipient);
    }

}
