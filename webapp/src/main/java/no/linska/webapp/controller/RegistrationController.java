package no.linska.webapp.controller;

import no.linska.webapp.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class RegistrationController {

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
            return modelAndView;
        }

        modelAndView.setViewName("registration_complete");
        modelAndView.addObject("successMessage",
                String.format("Bruker %s er registrert hos Linska, " +
                        "sjekk innboksen din for å verifisere kontoen ", user.getEmail()));
        modelAndView.addObject("");
        return modelAndView;
    }

}
