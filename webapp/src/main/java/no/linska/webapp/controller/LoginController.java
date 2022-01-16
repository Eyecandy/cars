package no.linska.webapp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser" ) {
            modelAndView.setViewName("login");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/hello");
        }
    }
}
