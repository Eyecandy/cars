package no.linska.webapp.controller;


import no.linska.webapp.entity.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String login() {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken) {
            return "/login";
        }

        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return "redirect:/hello";
    }

}
