package no.linska.webapp.service;

import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.entity.PriceRequestOrder;
import no.linska.webapp.entity.User;
import no.linska.webapp.repository.PriceRequestOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceRequestOrderServiceImpl implements PriceRequestOrderService{

    @Autowired
    UserServiceImpl userService;

    @Autowired
    PriceRequestOrderRepository priceRequestOrderRepository;

    @Override
    public List<PriceRequestOrder> getOrdersForUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        return priceRequestOrderRepository.findByUserId(user.getId());
    }


    public void createPriceRequestOrder(PriceRequest priceRequest) {
        int carBrandId = priceRequest.getCarBrandId();




    }
}
