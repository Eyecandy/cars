package no.linska.webapp.controller;

import no.linska.webapp.service.PriceRequestOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class PriceRequestOrderController {

    @Autowired
    PriceRequestOrderService priceRequestOrderService;

    @GetMapping("/price_request_order")
    public ModelAndView getPriceRequests() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("priceRequestOrders");
        modelAndView.addObject("priceRequestOrders", priceRequestOrderService.getOrdersForUser());
        return modelAndView;
    }
}
