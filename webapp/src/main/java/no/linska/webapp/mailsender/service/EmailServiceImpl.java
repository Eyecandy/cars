package no.linska.webapp.mailsender.service;


import lombok.extern.slf4j.Slf4j;
import no.linska.webapp.entity.CarBrand;
import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.entity.PriceRequestOrder;
import no.linska.webapp.repository.CarBrandRepository;
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
import java.util.Set;
import java.util.stream.Collectors;


@Component
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;


    final String NO_REPLY_EMAIL = "joakim.linska@yahoo.com";

    @Autowired
    CarBrandRepository carBrandRepository;



    public void sendSimpleMessage(String to, String subject, String text) {
        CarBrand carBrand = new CarBrand();
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

    public void sendMailToSellers(Set<PriceRequestOrder> priceRequestOrders, PriceRequest priceRequest) {
        System.out.println("CONFIG METHOD: " + priceRequest.getConfigMethod().getId() );
        Set<String> emails = getSellerEmails(priceRequestOrders);
        if (priceRequest.getConfigMethod().getId() == 1) {
            sendMailToSellerWithLink(emails,priceRequest.getConfiguration());
        }
        else {
            sendMailToSellerWithAttachment();
        }


    }

    private void sendMailToSellerWithLink(Set<String> emails, String url) {

        System.out.println("send mail with link");
        System.out.println(emails);
        System.out.println(url);


    }

    private void sendMailToSellerWithAttachment() {
        System.out.println("send mail with attachment");
    }

    private Set<String> getSellerEmails(Set<PriceRequestOrder> priceRequestOrders) {
        return priceRequestOrders
                .stream()
                .map(priceRequestOrder -> priceRequestOrder
                        .getSeller()
                        .getUser()
                        .getEmail()).collect(Collectors.toSet());

    }


}