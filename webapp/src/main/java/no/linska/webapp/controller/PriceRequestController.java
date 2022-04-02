package no.linska.webapp.controller;

import no.linska.webapp.entity.PriceRequest;
import no.linska.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.List;

@Controller
public class PriceRequestController {

    @Autowired
    PriceRequestService priceRequestService;

    @Autowired
    PriceRequestOrderService priceRequestOrderService;

    @Autowired
    CountyServiceImpl countyService;

    @Autowired
    CarBrandServiceImpl carBrandService;

    @Autowired
    ConfigMethodServiceImpl configMethodService;

    @Autowired
    StorageService storageService;



    @GetMapping("/pricerequest")
    public ModelAndView priceRequest() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("priceRequest", new PriceRequest());
        modelAndView.addObject("configMethods",configMethodService.getAllConfigMethods());
        modelAndView.addObject("counties",countyService.getAllCounties());
        modelAndView.addObject("carBrands",carBrandService.getAllCarBrands());
        modelAndView.setViewName("pricerequest");


        return modelAndView;
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
        if (priceRequest.getConfigMethod().getId() == 2) {
            Path storedPath = storageService.store(priceRequest.getFileConfiguration());
            priceRequest.setConfiguration(storedPath.toString());
        }
        priceRequestService.save(priceRequest);
        try {
            priceRequestOrderService.createPriceRequestOrders(priceRequest);
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
