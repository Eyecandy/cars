package no.linska.webapp.controller;

import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.service.PriceRequestOrderService;
import no.linska.webapp.service.PriceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PriceRequestController {

    @Autowired
    PriceRequestService priceRequestService;

    @Autowired
    PriceRequestOrderService priceRequestOrderService;

    @GetMapping("/pricerequest")
    public String priceRequest() {

        return "/pricerequest";
    }

    @PostMapping("/pricerequest")
    public ModelAndView createPriceRequest(@Valid PriceRequest priceRequest, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("pricerequest");
            modelAndView.addObject("priceRequest", priceRequest);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }
        priceRequestService.save(priceRequest);
        try {
            priceRequestOrderService.createPriceRequestOrdersAsync(priceRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return new ModelAndView("/list_price_request");


    }

    @GetMapping("/list_price_request")
    public ModelAndView getPriceRequests() {

        ModelAndView modelAndView = new ModelAndView();

        List<PriceRequest> priceRequests = priceRequestService.getUserPriceRequest();

        modelAndView.addObject("priceRequests",priceRequestService.getUserPriceRequest());

        return modelAndView;
    }





}
