package no.linska.webapp.controller;

import no.linska.webapp.dto.UserRegistrationDto;
import no.linska.webapp.entity.User;
import no.linska.webapp.entity.UserRole;
import no.linska.webapp.service.StorageService;
import no.linska.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class RestRegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    StorageService storageService;


    @PostMapping("/api/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBuyer(@RequestBody @Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult) {
        //TODO: HANDLE ERROR IN BINDING RESULT
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
        }

        User user = new User();
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

    }

}
