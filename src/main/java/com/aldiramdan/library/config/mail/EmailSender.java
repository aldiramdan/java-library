package com.aldiramdan.library.config.mail;

import com.aldiramdan.library.model.entity.User;
import jakarta.mail.MessagingException;

public interface EmailSender {
    void sendMail(User user, String tokenCode, String path, String subject) throws MessagingException;
}
