package no.linska.webapp.mailsender.service;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public interface EmailService {
    void sendMessage(String to,
                           String subject,
                           String text);

    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text,
                                   File file);







}
