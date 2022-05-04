package no.linska.webapp.service;

import no.linska.webapp.entity.*;
import no.linska.webapp.exception.DataException;
import no.linska.webapp.exception.reason.ProcessingException;
import no.linska.webapp.exception.reason.Reason;
import no.linska.webapp.mailsender.service.EmailServiceImpl;
import no.linska.webapp.repository.CarBrandRepository;
import no.linska.webapp.repository.PriceRequestOrderRepository;
import no.linska.webapp.repository.PriceRequestRepository;
import no.linska.webapp.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@EnableAsync
public class PriceRequestOrderServiceImpl implements PriceRequestOrderService {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    CarBrandRepository carBrandRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    PriceRequestOrderRepository priceRequestOrderRepository;

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    PriceRequestRepository priceRequestRepository;

    @Override
    public List<PriceRequestOrder> getOrdersBelongingToSellerUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        return priceRequestOrderRepository.findByUserId(user.getId());
    }




    public void createPriceRequestOrders(PriceRequest priceRequest) {

        CarBrand carBrand = fetchCarBrand(priceRequest.getCarBrand().getId());

        Collection<Retailer> retailers = fetchRetailers(carBrand);

        Set<Seller> sellers = fetchSellers(retailers);

        Set<PriceRequestOrder> priceRequestOrders = new HashSet<>();



        for (Seller seller : sellers) {
            PriceRequestOrder priceRequestOrder = new PriceRequestOrder();
            priceRequestOrder.setPriceRequest(priceRequest);
            priceRequestOrder.setSeller(seller);
            priceRequestOrders.add(priceRequestOrder);
        }

        priceRequest.setNumRetailersSentTo(priceRequestOrders.size());
        priceRequest.setNumRetailersAnswered(0);

        priceRequestOrderRepository.saveAll(priceRequestOrders);
        priceRequestRepository.save(priceRequest);




        sendEmailsAsync(priceRequestOrders,priceRequest);
    }

    public PriceRequestOrder getPriceRequestOrder(Long priceRequestOrderId) {
        Optional<PriceRequestOrder> priceRequestOrder = priceRequestOrderRepository.findById(priceRequestOrderId);
        if (priceRequestOrder.isEmpty()) {
            String message = "priceRequestOrderId: " + priceRequestOrder + " doesn't exist";
            throw new ProcessingException(Reason.PRICE_REQUEST_ORDER_DOES_NOT_EXIST,message);
        }

        priceBelongToUserCheck(priceRequestOrder.get());
        return priceRequestOrder.get();

    }



    @Async
    private void sendEmailsAsync(Set<PriceRequestOrder> priceRequestOrders ,PriceRequest priceRequest) {
        emailService.sendMailToSellers(priceRequestOrders,priceRequest);
    }




    private void priceBelongToUserCheck(PriceRequestOrder priceRequestOrder) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user.getId() != priceRequestOrder.getSeller().getUser().getId()) {

            String msg = String.format("PriceRequestOrder doesn't belong to user. user id: %s priceRequest id: %s", user.getId(), priceRequestOrder.getId());
            throw new ProcessingException(Reason.PRICE_REQUEST_DOES_NOT_BELONG_TO_USER, msg);
        }


    }

    private CarBrand fetchCarBrand(Integer carBrandId) {

        Optional<CarBrand> carBrandOptional = carBrandRepository.findById(carBrandId);

        if (carBrandOptional.isEmpty()) {
            Reason reason = Reason.CAR_BRAND_IS_EMPTY;
            throw new DataException(reason, reason.getMessage(carBrandId));
        }
        return carBrandOptional.get();
    }

    private Collection<Retailer> fetchRetailers(CarBrand carBrand) {
        Collection<Retailer> retailers = carBrand.getRetailers();
        if (retailers.isEmpty()) {
            Reason reason = Reason.NO_RETAILERS_IN_CAR_BRAND;
            throw new DataException(reason, reason.getMessage(carBrand));
        }
        return retailers;
    }

    private Set<Seller> fetchSellers(Collection<Retailer> retailers) {
        Optional<Set<Seller>> sellersOptional = sellerRepository.findByRetailer(retailers);
        if (sellersOptional.isEmpty()) {
            Reason reason = Reason.NO_SELLERS_IN_RETAILERS;
            throw new DataException(reason,reason.getMessage(retailers));
        }
        return sellersOptional.get();
    }

}
