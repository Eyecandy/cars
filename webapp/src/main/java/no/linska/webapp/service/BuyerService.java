package no.linska.webapp.service;

import no.linska.webapp.dto.BuyerUserDto;
import no.linska.webapp.entity.Buyer;

public interface BuyerService {
    void updateBuyerInfo(BuyerUserDto buyerUserDto);
    Buyer getBuyerByUserId(Long userId);
    void create(Buyer buyer);

}
