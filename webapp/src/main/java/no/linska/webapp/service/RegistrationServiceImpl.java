package no.linska.webapp.service;


import no.linska.webapp.entity.User;
import no.linska.webapp.exception.EmailExistsException;
import no.linska.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void register(User user) {
       if (userRepository.findByEmail(user.getEmail()) == null) {
           userRepository.save(user);
       }
    }
}
