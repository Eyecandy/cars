package no.linska.webapp.service;

import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.entity.PriceRequestOrder;

import java.util.List;

public interface PriceRequestOrderService {

    List<PriceRequestOrder> getOrdersForUser();
    void createPriceRequestOrdersAsync(PriceRequest priceRequest) throws InterruptedException;
}
