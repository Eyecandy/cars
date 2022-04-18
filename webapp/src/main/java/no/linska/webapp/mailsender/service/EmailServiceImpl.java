package no.linska.webapp.mailsender.service;


import lombok.extern.slf4j.Slf4j;
import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.entity.PriceRequestOrder;
import no.linska.webapp.repository.CarBrandRepository;
import no.linska.webapp.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
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


    final String NO_REPLY_EMAIL = "NO-REPLY@digibil.no";

    @Autowired
    CarBrandRepository carBrandRepository;

    @Autowired
    StorageService storageService;



    public void sendMessage(String to,String subject,String text) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(NO_REPLY_EMAIL);
            helper.setTo(to);
            helper.setText(text, false);
            helper.setSubject(subject);
            javaMailSender.send(message);
            log.info("SENT MESSAGE WITH ATTACHMENT");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void sendMessageWithAttachment(String to,String subject,String text,File file) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(NO_REPLY_EMAIL);
            helper.setTo(to);
            helper.setText(text, false);
            helper.addAttachment("bil-konfig.pdf" , file);
            helper.setSubject(subject);
            javaMailSender.send(message);
            log.info("SENT MESSAGE WITH ATTACHMENT");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMailToSellers(Set<PriceRequestOrder> priceRequestOrders, PriceRequest priceRequest) {
        System.out.println("CONFIG METHOD: " + priceRequest.getConfigMethod().getName() );
        Set<String> emails = getSellerEmails(priceRequestOrders);
        if (priceRequest.getConfigMethod().getId() == 1) {
            String text = createText(priceRequest) + "\nLink til konfigurasjon: " + priceRequest.getConfiguration();

            sendMailToSellerWithLink(emails,text);
        }
        else {

            sendMailToSellerWithAttachment(emails,createText(priceRequest) ,storageService.readFile(priceRequest.getConfiguration()));
        }


    }

    private String createText(PriceRequest priceRequest) {
        String isStuddedTireRequested = "Nei";

        String studdedTires = "Vil ha dekktype " + priceRequest.getTireOption().getName();
        String county = "Fylke som bilen skal leveres til: " + priceRequest.getCounty().getName();
        return studdedTires + "\n" + county;

    }

    private void sendMailToSellerWithLink(Set<String> emails, String text) {
        for (String email: emails) {
            sendMessage(email,
                    "Bil konfigurasjon på vegne av DigiBil",
                    text);
        }


    }

    private void sendMailToSellerWithAttachment(Set<String> emails, String text, File file) {
        for (String email : emails) {
            sendMessageWithAttachment(email,"Bil konfigurasjon på vegne av DigiBil",text,file);
        }

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