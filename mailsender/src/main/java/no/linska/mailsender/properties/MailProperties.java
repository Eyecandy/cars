package no.linska.mailsender.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


@Component
public class MailProperties {

    @Value("${spring.mail.host}")
    @NonNull
    private String host;

    @Value("${spring.mail.port}")
    @NonNull
    private String port;

    @Value("${spring.mail.username}")
    @NonNull
    private String userName;

    @Value("${spring.mail.password}")
    @NonNull
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    @NonNull
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    @NonNull
    private String ttlsEnable;

    @NonNull
    public String getHost() {
        return host;
    }

    @NonNull
    public String getPort() {
        return port;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    @NonNull
    public String getAuth() {
        return auth;
    }

    @NonNull
    public String getTtlsEnable() {
        return ttlsEnable;
    }
}
