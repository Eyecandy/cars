package no.linska.webapp.service;

import no.linska.webapp.entity.Buyer;
import no.linska.webapp.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements  BuyerService{

    @Autowired
    BuyerRepository buyerRepository;

    @Override
    public void save(Buyer buyer) {
        buyerRepository.save(buyer);
    }
}
