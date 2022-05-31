package no.linska.webapp.service;

import no.linska.webapp.entity.Buyer;
import no.linska.webapp.exception.reason.ProcessingException;
import no.linska.webapp.exception.reason.Reason;
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

    public Buyer getBuerByUserId(Long userId) {
        var buyer = buyerRepository.findByUser(userId);
        if (buyer.isEmpty()) {
            throw new ProcessingException(Reason.NO_SELLER_WITH_THAT_USER_ID, userId.toString());
        }
        return buyer.get();

    }
}
