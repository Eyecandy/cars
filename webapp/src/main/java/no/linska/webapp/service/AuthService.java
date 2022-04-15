package no.linska.webapp.service;

import no.linska.webapp.configuration.WebSecurityConfig;
import no.linska.webapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserService userService;



    public boolean isValidCredentials(String username,String password) {
        User user = userService.findByEmail(username);
        if (user == null) {
            return false;
        }

       return WebSecurityConfig.passwordEncoder().matches(password, user.getPassword());
    }
}
