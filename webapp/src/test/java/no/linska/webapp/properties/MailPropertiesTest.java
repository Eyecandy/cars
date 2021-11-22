package no.linska.webapp.properties;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource(properties = {"MAIL_USERNAME=3025",
        "MAIL_PASSWORD=localhost"})

class MailPropertiesTest {

    @Test
    void shouldDoThis() {


    }




}