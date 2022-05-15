package no.linska.webapp.service;


import no.linska.webapp.dto.PriceRequestDto;
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

import java.text.SimpleDateFormat;
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



    SimpleDateFormat DateFor = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat TimeFor = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    SimpleDateFormat formatter2 = new SimpleDateFormat("mmddssMM");
    Calendar calendar = Calendar.getInstance();



    public void save(PriceRequest priceRequest) throws InterruptedException {

        int arbitraryStartOrderNumber = 2437;
        calendar.add(Calendar.HOUR, 48);  // advances day by 2
        priceRequest.setDeadline(calendar.getTime());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        priceRequest.setOrderNumber(formatter2.format(calendar.getTime()) + "-" +(user.getId() +  arbitraryStartOrderNumber ));
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


       return optionalPriceRequest.get();
    }

    public PriceRequestDto convertPriceRequest(PriceRequest priceRequest) {
        PriceRequestDto priceRequestDto = new PriceRequestDto();
        priceRequestDto.setCounty(priceRequest.getCounty());

        Calendar c = Calendar.getInstance();
        String stringDate = DateFor.format(priceRequest.getDeadline());
        String stringTime = TimeFor.format(priceRequest.getDeadline());
        priceRequestDto.setDeadlineDatePretty(stringDate);
        priceRequestDto.setDeadlineTimePretty(stringTime);
        priceRequestDto.setDeadline(priceRequest.getDeadline());
        priceRequestDto.setTireOption(priceRequest.getTireOption());
        priceRequestDto.setConfigMethod(priceRequest.getConfigMethod());
        priceRequestDto.setLink(priceRequest.getConfiguration());
        priceRequestDto.setId(priceRequest.getId());
        priceRequestDto.setDeadLineReached(priceRequest.getDeadline().before(c.getTime()));
        priceRequestDto.setCarBrandName(priceRequest.getCarBrand().getName());
        priceRequestDto.setNumRetailersAnswered(priceRequest.getNumRetailersAnswered());
        priceRequestDto.setNumRetailersSentTo(priceRequest.getNumRetailersSentTo());
        priceRequestDto.setConfiguration(priceRequest.getConfiguration());
        priceRequestDto.setOrderNumber(priceRequest.getOrderNumber());


        return priceRequestDto;
    }

    public void priceBelongToUserCheck(PriceRequest priceRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user.getId() != priceRequest.getUser().getId()) {

            String msg = String.format("Pricerequest doesnt belong to user. user id: %s priceRequest id: %s", user.getId(), priceRequest.getId());
            throw new ProcessingException(Reason.PRICE_REQUEST_DOES_NOT_BELONG_TO_USER, msg);
        }


    }

    public boolean isDeadlineReached(PriceRequest priceRequest) {
        return priceRequest.getDeadline().after(calendar.getTime());
    }




}
