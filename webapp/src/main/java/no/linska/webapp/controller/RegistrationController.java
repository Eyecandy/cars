package no.linska.webapp.controller;

import no.linska.webapp.entity.User;
import no.linska.webapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    @Autowired
    UserServiceImpl userService;

    final String USER_ALREADY_EXISTS_KEY = "reg_conflict";
    final String USER_ALREADY_EXISTS_ERR_MSG = "Bruker er %s finnes allerede";
    final String USER_REGISTERED_SUCCESS_KEY = "reg_success";
    final String USER_REGISTERED_SUCCESS_MSG = "Bruker %s er registrert hos Linska, sjekk innboksen din for å verifisere kontoen";



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "registration_form";
    }


    @PostMapping(value = "/register")
    public ModelAndView createNewUser(@Valid User user,
                                      BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration_form");
            modelAndView.addObject("user",user);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }
        try {
            userService.register(user); }
        catch (DataIntegrityViolationException e) {
            modelAndView.setViewName("registration_form");
            modelAndView.setStatus(HttpStatus.CONFLICT);
            bindingResult.rejectValue("email",String.format(USER_ALREADY_EXISTS_ERR_MSG,user.getEmail()));
            return modelAndView;
        }
        modelAndView.setViewName("registration_complete");
        modelAndView.addObject(USER_REGISTERED_SUCCESS_KEY,
                String.format(USER_REGISTERED_SUCCESS_MSG, user.getEmail()));
        modelAndView.addObject("");
        return modelAndView;
    }

}
