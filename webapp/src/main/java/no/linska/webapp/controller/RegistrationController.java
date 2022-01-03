package no.linska.webapp.controller;

import no.linska.webapp.entity.User;
import no.linska.webapp.service.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    @Autowired
    RegistrationServiceImpl registrationService;



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "registration_form";
    }


    @PostMapping(value = "/process_register")
    public ModelAndView createNewUser(@Valid User user,
                                      BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration_form");
            modelAndView.addObject("user",user);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }

        registrationService.register(user);

        modelAndView.setViewName("registration_complete");
        modelAndView.addObject("successMessage",
                String.format("Bruker %s er registrert hos Linska, " +
                        "sjekk innboksen din for å verifisere kontoen ", user.getEmail()));
        modelAndView.addObject("");
        return modelAndView;
    }

}
