package no.linska.webapp.controller;


import no.linska.webapp.dto.BuyerUserDto;
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


        return ResponseEntity.ok().body("success");

    }
    @GetMapping("/get")
    public ResponseEntity<?> get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        var buyer = buyerService.getBuyerByUserId(user.getId());
        var buyerUserDto = new BuyerUserDto();
        buyerUserDto.setEmail(user.getEmail());
        buyerUserDto.setFirstName(user.getFirstName());
        buyerUserDto.setLastName(user.getLastName());
        buyerUserDto.setPhoneNumber(user.getPhoneNumber());
        buyerUserDto.setCity(buyer.getCity());
        buyerUserDto.setPostBox(buyer.getPostBox());
        buyerUserDto.setStreetName(buyer.getStreetName());
        buyerUserDto.setStreetNumber(buyer.getStreetNumber());
        return ResponseEntity.ok().body(buyerUserDto);

    }



}
