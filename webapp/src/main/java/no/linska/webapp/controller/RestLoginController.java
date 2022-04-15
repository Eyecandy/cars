package no.linska.webapp.controller;

import no.linska.webapp.dto.LoginRequestDto;
import no.linska.webapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class RestLoginController {

    @Autowired
    AuthService authService;

    @PostMapping("/api/auth/login")
    public void loginBuyer(@RequestBody @Valid LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        //TODO: HANDLE ERROR IN BINDING RESULT
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }
        System.out.println(loginRequestDto.getEmail());
        System.out.println(loginRequestDto.getPassword());

        System.out.println(authService.isValidCredentials(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

    }


}
