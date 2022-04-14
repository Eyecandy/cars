package no.linska.webapp.service;


import no.linska.webapp.configuration.WebSecurityConfig;
import no.linska.webapp.entity.User;
import no.linska.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void register(User user) {
        String encodedPassword = WebSecurityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);


    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }




}
