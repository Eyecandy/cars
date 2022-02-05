package no.linska.webapp.controller;

import no.linska.webapp.properties.StorageProperties;
import no.linska.webapp.repository.UserRepository;
import no.linska.webapp.service.UserService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestExecution;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationControllerIntegrationTest {

    //TODO: test user folder creation on registration

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    String REQUEST = "/register?email=%s&password=%s&matchingPassword=%s";

    @Autowired
    StorageProperties storageProperties;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @AfterAll
    void removeTestFolders() throws IOException {
        FileUtils.deleteDirectory(new File(storageProperties.getUploadDir()));
    }


    @Test
    void csfrIsEnabled_onRegistration() throws Exception {
        System.out.println(userRepository.findAll());
        String validEmail = "user@server.com";
        String validPassword = "between_8_and_30_chars";
        String nonMatchingPassword = "between_8_and_30_chars";


        String request = String
                .format(REQUEST, validEmail, validPassword, nonMatchingPassword);

        this.mvc.perform(post(request))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldRegisterUser() throws Exception {
        String validEmail = "user@server.com";
        String validPassword = "between_8_and_30_chars";

        String request = String
                .format(REQUEST, validEmail, validPassword, validPassword);

        this.mvc.perform(post(request).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotRegister_whenPasswordToShort() throws Exception {
        String validEmail = "user@server.com";
        String validPassword = "2short";
        String request = String
                .format(REQUEST, validEmail, validPassword, validPassword);

        this.mvc.perform(post(request).with(csrf()))
                .andExpect(status().isBadRequest());

    }

    @Test
    void shouldNotRegister_whenInvalidEmail() throws Exception {
        String invalidEmail = "user@server";
        String validPassword = "between_8_and_30_chars";
        String request = String
                .format("/register?email=%s&password=%s", invalidEmail, validPassword, validPassword);

        this.mvc.perform(post(request).with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotRegisterUser_whenAlreadyRegistered() throws Exception {
        var validEmail = "user@server.com";
        var validPassword = "between_8_and_30_chars";
        var request = String
                .format(REQUEST, validEmail, validPassword, validPassword);

        this.mvc.perform(post(request).with(csrf()))
                .andExpect(status().isOk());

        this.mvc.perform(post(request).with(csrf()))
                .andExpect(status().isConflict());

    }


    @Test
    void shouldNotRegister_whenNonMatchingPassword() throws Exception {
        String validEmail = "user@server.com";
        String validPassword = "between_8_and_30_chars";
        String nonMatchingPassword = "NOT_A_MATCH";

        String request = String
                .format(REQUEST, validEmail, validPassword, nonMatchingPassword);

        this.mvc.perform(post(request).with(csrf()))
                .andExpect(status().isBadRequest());
    }
}
