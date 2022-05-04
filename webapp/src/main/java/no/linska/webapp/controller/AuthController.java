package no.linska.webapp.controller;


import no.linska.webapp.dto.EmailDto;
import no.linska.webapp.dto.LoginRequestDto;
import no.linska.webapp.dto.RegistrationRequestDto;
import no.linska.webapp.entity.User;
import no.linska.webapp.entity.UserRole;
import no.linska.webapp.mailsender.service.EmailService;
import no.linska.webapp.repository.UserRoleRepository;
import no.linska.webapp.responses.JwtResponse;
import no.linska.webapp.responses.MessageResponse;
import no.linska.webapp.security.jwt.JwtUtils;
import no.linska.webapp.service.StorageService;
import no.linska.webapp.service.UserDetailsImpl;
import no.linska.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    UserRoleRepository userRoleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    StorageService storageService;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("BINDING RESULT ERROR");
            System.out.println(bindingResult.getAllErrors());
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
            System.out.println(bindingResult.getAllErrors());
        }

        User user = userService.findByEmail(userRegistrationDto.getEmail());

        if (user != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
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
        storageService.createUserDir(user.getId().toString());

        return ResponseEntity.ok().body(new MessageResponse("Din e-post: " + user.getEmail() +" er registrert"));
    }


    @PostMapping("/signupretailer")
    public ResponseEntity<?> registerUserSeller(@RequestBody @Valid  RegistrationRequestDto userRegistrationDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }

        User user = userService.findByEmail(userRegistrationDto.getEmail());

        if (user != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
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

        return ResponseEntity.ok().body(new MessageResponse("Din e-post: " + user.getEmail() +" er registrert"));
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
        return ResponseEntity.ok().body("");
    }

}