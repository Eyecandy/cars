package no.linska.webapp.service;


import no.linska.webapp.configuration.WebSecurityConfig;
import no.linska.webapp.entity.User;
import no.linska.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void register(User user) {
        String encodedPassword = WebSecurityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setMatchingPassword(encodedPassword);
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        List<User> userList = userRepository.findByEmail(email);

        System.out.println(userList.size());
        System.out.println(userList.get(0));
        return userList.get(0);
        //TODO: create null check
        // It should not be possible that are more than one user
        // because there is a unique constrain in email field in User Class.

    }


}
