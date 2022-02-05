package no.linska.webapp.service;

import no.linska.webapp.entity.User;

public interface UserService {

    void register(User user);

    User findByEmail(String email);

}
