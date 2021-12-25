package no.linska.webapp.controller;

import no.linska.webapp.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "registration_form";
    }


    @RequestMapping(value = "/process_register", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid @ModelAttribute("user") User user,
                                      BindingResult bindingResult) {

        System.out.println(user.getFirstName());
        System.out.println(user.getPassword());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) { // 1.
                String fieldErrors = ((FieldError) error).getField(); // 2.
                System.out.println(fieldErrors);
            }
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("successMessage", "Your Registration is complete");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration_complete");

        return modelAndView;
    }

}
