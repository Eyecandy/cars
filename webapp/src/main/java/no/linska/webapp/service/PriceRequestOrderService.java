package no.linska.webapp.service;

import no.linska.webapp.entity.PriceRequestOrder;

import java.util.List;

public interface PriceRequestOrderService {

    List<PriceRequestOrder> getOrdersForUser();
}
