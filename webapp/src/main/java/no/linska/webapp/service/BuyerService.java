package no.linska.webapp.service;

import no.linska.webapp.entity.Buyer;

public interface BuyerService {
    void save(Buyer buyer);
    Buyer getBuerByUserId(Long userId);
}
