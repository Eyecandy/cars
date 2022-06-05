package no.linska.webapp.prebuilt;

import no.linska.webapp.entity.User;

public class UserBuilder {

    public static User createValidUser() {
        String validEmail = "user@server.com";
        String validPassword = "between_8_and_30_chars";
        User user = new User();
        user.setEmail(validEmail);
        user.setPassword(validPassword);

        return user;
    }

    public static User createValidSellerUser() {
        String validEmail = "seller@server.com";
        String validPassword = "between_8_and_30_chars";
        User user = new User();
        user.setId(2L);

        user.setEmail(validEmail);
        user.setPassword(validPassword);

        return user;
    }
}
