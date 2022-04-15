package no.linska.webapp.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/buyer")
public class RestPriceRequestController {



    @GetMapping("/pricerequest")
    public void getPriceRequest() {

    }


}
