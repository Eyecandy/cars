package no.linska.webapp.service;

import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.entity.PriceRequestOrder;

import java.util.List;

public interface PriceRequestOrderService {

    List<PriceRequestOrder> getOrdersBelongingToSellerUser();
    void createPriceRequestOrders(PriceRequest priceRequest) throws InterruptedException;
    PriceRequestOrder getPriceRequestOrder(Long priceRequestOrderId);
    List<PriceRequestOrder> getHighestAndLowest(List<PriceRequestOrder> priceRequestOrders);
    List<PriceRequestOrder> getPriceRequestOrdersBelongingTo(Long priceRequestId);
    String getFilePathOfBestOffer(PriceRequest priceRequest);
    PriceRequestOrder getLowestPriceRequestOrder(PriceRequest priceRequest);




}
