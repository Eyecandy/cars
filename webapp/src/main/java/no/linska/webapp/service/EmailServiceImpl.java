package no.linska.webapp.service;

import no.linska.webapp.properties.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;


    @Autowired
    private StorageProperties storageProperties;

    final String NO_REPLY_EMAIL = "joakim.linska@yahoo.com";


    public void sendSimpleMessage(String to, String subject, String text) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NO_REPLY_EMAIL);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            javaMailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }



    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {

    }

}