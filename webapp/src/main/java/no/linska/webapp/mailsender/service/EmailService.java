package no.linska.webapp.mailsender.service;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public interface EmailService {
    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendMessageWithAttachment(String to,
                                   File file);





}
