package no.linska.webapp.service;


import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.entity.User;
import no.linska.webapp.exception.reason.ProcessingException;
import no.linska.webapp.exception.reason.Reason;
import no.linska.webapp.repository.PriceRequestRepository;
import no.linska.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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







    public void save(PriceRequest priceRequest) throws InterruptedException {

        Calendar c = Calendar.getInstance(); // starts with today's date and time
        c.add(Calendar.HOUR, 48);  // advances day by 2
        priceRequest.setDeadline(c.getTime());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        priceRequest.setUser(user);
        priceRequestRepository.save(priceRequest);


    }


    public List<PriceRequest> getUserPriceRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        return user.getPriceRequestList();
    }

    public PriceRequest getPriceRequest(Long priceRequestId) {
        var optionalPriceRequest = priceRequestRepository.findById(priceRequestId);
        if (optionalPriceRequest.isEmpty()) {
            throw new ProcessingException(Reason.PRICE_REQUEST_DOES_NOT_EXIST, priceRequestId.toString());
        }
        priceBelongToUserCheck(optionalPriceRequest.get());

       return optionalPriceRequest.get();
    }

    private void priceBelongToUserCheck(PriceRequest priceRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user.getId() != priceRequest.getUser().getId()) {

            String msg = String.format("Pricerequest doesnt belong to user. user id: %s priceRequest id: %s", user.getId(), priceRequest.getId());
            throw new ProcessingException(Reason.PRICE_REQUEST_DOES_NOT_BELONG_TO_USER, msg);
        }


    }




}
