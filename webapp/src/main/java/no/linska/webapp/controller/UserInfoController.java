package no.linska.webapp.controller;

import no.linska.webapp.dto.SellerUserDto;
import no.linska.webapp.entity.Seller;
import no.linska.webapp.entity.User;
import no.linska.webapp.service.SellerService;
import no.linska.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
public class UserInfoController {

    @Autowired
    UserService userService;

    @Autowired
    SellerService sellerService;



    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByEmail(auth.getName());
        System.out.println(user.getUserRole().getType());
        System.out.println(user.getUserRole().getType().equals("seller"));
        if (user.getUserRole().getType().equals("seller")) {
            System.out.println("seller");
            Seller seller = sellerService.getSellerByUserId(user.getId());
            seller.getRetailer().getName();
            SellerUserDto sellerUserDto = new SellerUserDto();
            sellerUserDto.setRetailerName(seller.getRetailer().getName());
            sellerUserDto.setEmail(user.getEmail());
            sellerUserDto.setFirstName(user.getFirstName());
            sellerUserDto.setLastName(user.getLastName());
            sellerUserDto.setPhoneNumber(user.getPhoneNumber());
            return ResponseEntity.ok().body(sellerUserDto);

        }
        else if (user.getUserRole().getType().equals("buyer")) {
            System.out.println("buyer");
        }
        System.out.println("end");
        return ResponseEntity.badRequest().body("bad request");
    }
}
