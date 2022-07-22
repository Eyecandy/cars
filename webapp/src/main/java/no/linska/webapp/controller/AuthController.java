package no.linska.webapp.controller;


import no.linska.webapp.dto.EmailDto;
import no.linska.webapp.dto.LoginRequestDto;
import no.linska.webapp.dto.RegistrationRequestDto;
import no.linska.webapp.entity.Buyer;
import no.linska.webapp.entity.User;
import no.linska.webapp.entity.UserRole;
import no.linska.webapp.mailsender.service.EmailService;
import no.linska.webapp.responses.CodeResponse;
import no.linska.webapp.responses.JwtResponse;
import no.linska.webapp.responses.HttpCodeDescription;
import no.linska.webapp.security.jwt.JwtUtils;
import no.linska.webapp.service.BuyerService;
import no.linska.webapp.service.StorageService;
import no.linska.webapp.service.UserDetailsImpl;
import no.linska.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    @Autowired
    StorageService storageService;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BuyerService buyerService;

    @Autowired
    EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new CodeResponse("login_failure"));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        JwtResponse jwtResponse = new  JwtResponse();
        jwtResponse.setEmail(userDetails.getEmail());
        jwtResponse.setId(userDetails.getId());
        jwtResponse.setToken(jwt);
        jwtResponse.setRoles(roles);
        jwtResponse.setTokenType("Bearer");
        return ResponseEntity.ok(jwtResponse);

    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid  RegistrationRequestDto userRegistrationDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            var errorMessageList = new ArrayList<String>();
            for (var errors:bindingResult.getAllErrors()) {
                errorMessageList.add(errors.getDefaultMessage());
            }
            return ResponseEntity
                    .badRequest()
                    .body(new CodeResponse("field_error",errorMessageList));

        }

        User user = userService.findByEmail(userRegistrationDto.getEmail());

        if (user != null) {
            return ResponseEntity
                    .status(HttpCodeDescription.CONFLICT_EMAIL_EXISTS.getHttpCode())
                    .body(HttpCodeDescription.CONFLICT_EMAIL_EXISTS.getCodeResponse());
        }
        user = new User();
        String email = userRegistrationDto.getEmail();
        String password = userRegistrationDto.getPassword();
        UserRole userRole = new UserRole();
        userRole.setType("buyer");
        userRole.setId(1);

        user.setEmail(email);
        user.setPassword(password);
        user.setUserRole(userRole);

        userService.register(user);
        Buyer buyer = new Buyer();
        buyer.setUser(user);
        buyerService.create(buyer);


        storageService.createUserDir(user.getId().toString());

        return ResponseEntity
                .status(HttpCodeDescription.CREATED.getHttpCode())
                .body(HttpCodeDescription.CREATED.getCodeResponse());
    }


    @PostMapping("/signupretailer")
    public ResponseEntity<?> registerUserSeller(@RequestBody @Valid  RegistrationRequestDto userRegistrationDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            var errorMessageList = new ArrayList<String>();
            for (var errors:bindingResult.getAllErrors()) {
                errorMessageList.add(errors.getDefaultMessage());
            }
            return ResponseEntity
                    .badRequest()
                    .body(new CodeResponse("field_error",errorMessageList));

        }

        User user = userService.findByEmail(userRegistrationDto.getEmail());

        if (user != null) {
            return ResponseEntity
                    .status(HttpCodeDescription.CONFLICT_EMAIL_EXISTS.getHttpCode())
                    .body(HttpCodeDescription.CONFLICT_EMAIL_EXISTS.getCodeResponse());
        }
        user = new User();
        String email = userRegistrationDto.getEmail();
        String password = userRegistrationDto.getPassword();
        UserRole userRole = new UserRole();
        userRole.setType("seller");
        userRole.setId(2);

        user.setEmail(email);
        user.setPassword(password);
        user.setUserRole(userRole);

        userService.register(user);
        storageService.createUserDir(user.getId().toString());

        return ResponseEntity
                .status(HttpCodeDescription.CREATED.getHttpCode())
                .body(HttpCodeDescription.CREATED.getCodeResponse());
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@RequestBody EmailDto emailHolder) {
        User user = userService.findByEmail(emailHolder.getEmail());

        if (user != null) {
            String generatedString = "temp_password";
            String text = "\nDitt nye passord er: " +generatedString + "\n";
            user.setPassword(generatedString);
            userService.register(user);
            emailService.sendMessage(emailHolder.getEmail(),"passord reset", text);
        }
        storageService.createUserDir(user.getId().toString());
        return ResponseEntity.ok().body(HttpCodeDescription.SUCCESS.getCodeResponse());
    }

}