package no.linska.mailsender.service;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Component
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;


    final String NO_REPLY_EMAIL = "NO-REPLY@digibil.no";





    public void sendSimpleMessage(String to, String subject, String text) {
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NO_REPLY_EMAIL);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
            log.info("SENT SIMPLE MESSAGE");
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }



    @Override
    public void sendMessageWithAttachment(String to,File file) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {

            helper = new MimeMessageHelper(message, true);
            helper.setFrom(NO_REPLY_EMAIL);
            helper.setTo(to);
            helper.setText("text", true);
            FileSystemResource fileSystemResource  = new FileSystemResource(file);
            helper.addAttachment(file.getName(), file);
            helper.setSubject("Hi - test");
            javaMailSender.send(message);
            log.info("SENT MESSAGE WITH ATTACHMENT");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}