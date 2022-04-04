package no.linska.webapp.service;


import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.entity.User;
import no.linska.webapp.repository.PriceRequestRepository;
import no.linska.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceRequestService {

    @Autowired
    PriceRequestRepository priceRequestRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    StorageService storageService;



    public void save(PriceRequest priceRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        priceRequest.setUser(user);
        priceRequestRepository.save(priceRequest);
    }

    public List<PriceRequest> getUserPriceRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        return new ArrayList<>(user.getPriceRequestList());
    }
}
