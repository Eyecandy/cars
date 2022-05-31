package no.linska.webapp.controller;


import no.linska.webapp.dto.BuyerUserDto;
import no.linska.webapp.entity.Buyer;
import no.linska.webapp.entity.User;
import no.linska.webapp.service.BuyerService;
import no.linska.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/buyer/info")
@RestController
public class BuyerInfoController {

    @Autowired
    UserService userService;
    @Autowired
    BuyerService buyerService;

    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody BuyerUserDto buyerUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        user.setFirstName(buyerUserDto.getFirstName());
        user.setLastName(buyerUserDto.getLastName());
        user.setPhoneNumber(buyerUserDto.getPhoneNumber());

        System.out.println("postBox: " + buyerUserDto.getPostBox());
        Buyer buyer = buyerService.getBuerByUserId(user.getId());
        buyer.setCity(buyerUserDto.getCity());
        buyer.setPostBox(buyerUserDto.getPostBox());
        buyer.setStreetName(buyerUserDto.getStreetName());
        buyer.setStreetNumber(buyerUserDto.getStreetNumber());

        buyerService.save(buyer);

        return ResponseEntity.ok().body("success");

    }



}
