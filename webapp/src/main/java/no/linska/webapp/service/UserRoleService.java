package no.linska.webapp.service;

import no.linska.webapp.entity.UserRole;
import no.linska.webapp.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;


    public UserRole findRoleById(UserRole userRole) {
        return userRoleRepository.findById(userRole.getId()).get();
    }
}
