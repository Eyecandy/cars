package no.linska.webapp.prebuilt;

import no.linska.webapp.entity.User;

public class UserBuilder {

    public static User getValidUser() {
        String validEmail = "user@server.com";
        String validPassword = "between_8_and_30_chars";
        User user = new User();
        user.setEmail(validEmail);
        user.setPassword(validPassword);
        user.setMatchingPassword(validPassword);
        return user;
    }
}
