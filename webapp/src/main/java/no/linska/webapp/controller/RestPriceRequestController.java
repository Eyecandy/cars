package no.linska.webapp.controller;


import no.linska.webapp.repository.TireOptionRepository;
import no.linska.webapp.service.CarBrandService;
import no.linska.webapp.service.ConfigMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/buyer")
public class RestPriceRequestController {

    @Autowired
    CarBrandService carBrandService;

    @Autowired
    TireOptionRepository tireOptionRepository;

    @Autowired
    ConfigMethodService configMethodService;


    @GetMapping("/pricerequest")
    public void getPriceRequest() {


    }

    @GetMapping("/carbrands")
    public ResponseEntity<?> getAvailableCarBrands() {

        return ResponseEntity.ok(List.of(carBrandService.getAllCarBrands(), tireOptionRepository.findAll(), configMethodService.getAllConfigMethods()));
    }



}
