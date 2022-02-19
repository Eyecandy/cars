package no.linska.webapp.controller;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import no.linska.webapp.entity.PriceRequest;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class PriceRequestController {

    @GetMapping("/pricerequest")
    public String priceReuqest() {
        return "/pricerequest";
    }

    @PostMapping("/pricerequest")
    public ModelAndView createPriceRequest(@Valid PriceRequest priceRequest, BindingResult bindingResult) {

        System.out.println("HERE" + priceRequest );
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors().toString());

        }
        return new ModelAndView("/pricerequest");


    }



}
