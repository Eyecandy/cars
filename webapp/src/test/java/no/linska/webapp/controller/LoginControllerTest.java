package no.linska.webapp.controller;


import no.linska.webapp.entity.User;
import no.linska.webapp.prebuilt.UserBuilder;
import no.linska.webapp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    public void givenRegisteredUser_shouldLoginAndRedirect() throws Exception {
        // register user
        User validUser = UserBuilder.getValidUser();
        String validEmail = validUser.getEmail();
        String validPassword = validUser.getPassword();
        String REQUEST = "/register?email=%s&password=%s&matchingPassword=%s";
        String request = String
                .format(REQUEST, validEmail, validPassword, validPassword);

        this.mvc.perform(post(request).with(csrf()));


        // login user
        RequestBuilder requestBuilder = formLogin("/process_login").user(validEmail).password(validPassword);
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hello"));

    }


    @Test
    public void givenUserNotRegistered_shouldFailToLogin() throws Exception {
        // register user
        User validUser = UserBuilder.getValidUser();
        String validEmail = validUser.getEmail();
        String validPassword = validUser.getPassword();


        // login user
        RequestBuilder requestBuilder = formLogin("/process_login").user(validEmail).password(validPassword);
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));

    }
}
