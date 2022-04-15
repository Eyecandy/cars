package no.linska.webapp.controller;

import no.linska.webapp.dto.UserLoginDto;
import no.linska.webapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RestLoginController {

    @Autowired
    AuthService authService;

    @PostMapping("/api/login")
    public void loginBuyer(@RequestBody @Valid UserLoginDto userLoginDto, BindingResult bindingResult) {
        //TODO: HANDLE ERROR IN BINDING RESULT
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }
        System.out.println(userLoginDto.getEmail());
        System.out.println(userLoginDto.getPassword());

        System.out.println(authService.isValidCredentials(userLoginDto.getEmail(),userLoginDto.getPassword()));

    }


}
