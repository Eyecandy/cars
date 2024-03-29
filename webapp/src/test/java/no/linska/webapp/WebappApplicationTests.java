package no.linska.webapp;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"MAIL_USERNAME=3025",
        "MAIL_PASSWORD=localhost"})
@ActiveProfiles("test")
class WebappApplicationTests {

    void contextLoads() {
    }

}
