package com.aldiramdan.library.config.mail.impl;

import com.aldiramdan.library.config.mail.EmailSender;
import com.aldiramdan.library.model.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${application.frontend.origin-url}")
    private String frontendOriginUrl;

    @Async
    @Override
    public void sendMail(User user, String tokenCode, String path, String subject) throws MessagingException {
        final String buildMail;
        final String templateMail;

        String link = String.format("%s/auth%s?token=%s", frontendOriginUrl, path, tokenCode);

        if (subject.contains("Confirm")) {
            templateMail = "auth/confirm-email.html";
        } else if (subject.contains("Recovery")) {
            templateMail = "auth/recovery-email.html";
        } else if (subject.contains("Forgot")) {
            templateMail = "auth/forgot-password-email.html";
        } else {
            templateMail = null;
        }

        Context context = new Context();
        if (tokenCode.length() == 6) {
            context.setVariable("name", user.getUsername());
            context.setVariable("code", tokenCode);
        } else {
            context.setVariable("name", user.getUsername());
            context.setVariable("link", link);
        }
        buildMail = templateEngine.process(templateMail, context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(buildMail, true);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setFrom("libary@email.com");
        javaMailSender.send(mimeMessage);
    }
}
