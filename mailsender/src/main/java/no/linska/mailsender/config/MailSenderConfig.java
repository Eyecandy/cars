package no.linska.mailsender.config;


import no.linska.mailsender.properties.MailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
public class MailSenderConfig {

    @Autowired
    MailProperties mailProperties;

    @Bean
    public JavaMailSenderImpl getJavaMailImplSender()  {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", mailProperties.getHost());
        properties.put("mail.smtp.port", mailProperties.getPort());
        properties.put("mail.smtp.starttls.enable", mailProperties.getTtlsEnable());
        properties.put("mail.smtp.auth",mailProperties.getAuth());

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(mailProperties.getUserName() ,
                        mailProperties.getPassword() );
            }

        });
        mailSender.setSession(session);
        session.setDebug(true);

        return mailSender;
    }
}
