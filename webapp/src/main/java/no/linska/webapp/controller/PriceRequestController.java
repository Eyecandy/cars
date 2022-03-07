package no.linska.webapp.controller;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.service.PriceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.Valid;

@Controller
public class PriceRequestController {

    @Autowired
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    PriceRequestService priceRequestService;

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
        return new ModelAndView("/list_price_request");


    }

    @GetMapping("/list_price_request")
    public String getPriceRequests() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("priceRequests",priceRequestService.getUserPriceRequest());

        return "/list_price_request";
    }





}
