package no.linska.webapp.controller;


import no.linska.webapp.entity.PriceRequestOrder;
import no.linska.webapp.service.PriceRequestOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/seller/pricerequestorder")
public class RestPriceRequestOrderController {
    @Autowired
    PriceRequestOrderService priceRequestOrderService;

    @GetMapping("/listpricerequestorders")
    public ResponseEntity<?> getSellerPriceRequestOrder() {
        System.out.println("IN HERE?");
        List<PriceRequestOrder> priceRequestOrderList = priceRequestOrderService.getOrdersBelongingToSellerUser();
        System.out.println("priceRequestOrder: " +priceRequestOrderList.size());
        return ResponseEntity.ok().body(priceRequestOrderList);
    }

}
