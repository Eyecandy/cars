package no.linska.webapp.service;

import no.linska.webapp.entity.Seller;
import no.linska.webapp.exception.reason.ProcessingException;
import no.linska.webapp.exception.reason.Reason;
import no.linska.webapp.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    public Seller getSellerByUserId(Long userId) {
        var seller = sellerRepository.findByUser(userId);
        if (seller.isEmpty()) {
            throw new ProcessingException(Reason.NO_SELLER_WITH_THAT_USER_ID, userId.toString());
        }
        return seller.get();

    }
}
