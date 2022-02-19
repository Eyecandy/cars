package no.linska.webapp.controller;

import no.linska.webapp.entity.User;
import no.linska.webapp.entity.UserRole;
import no.linska.webapp.service.StorageService;
import no.linska.webapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class RegistrationController {

    final String USER_ALREADY_EXISTS_KEY = "reg_conflict";
    final String USER_ALREADY_EXISTS_ERR_MSG = "Bruker er %s finnes allerede";
    final String USER_REGISTERED_SUCCESS_KEY = "reg_success";
    final String USER_REGISTERED_SUCCESS_MSG = "Bruker %s er registrert hos Linska, sjekk innboksen din for å verifisere kontoen";
    @Autowired
    UserServiceImpl userService;

    @Autowired
    StorageService storageService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        selectDefaultUserRoleRadioButton(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "register";
        }
        return "redirect:/hello";
    }


    @PostMapping(value = "/register")
    public ModelAndView createNewUser(@Valid User user,
                                      BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
            modelAndView.addObject("user", user);
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }

        else if (existing(user)) {
            modelAndView.setViewName("register");
            modelAndView.addObject("user", user);
            modelAndView.setStatus(HttpStatus.CONFLICT);
            modelAndView
                    .addObject(USER_ALREADY_EXISTS_KEY,
                            String.format(USER_ALREADY_EXISTS_ERR_MSG,
                                    user.getEmail()
                            )
                    );
            return modelAndView;
        }

        userService.register(user);
        storageService.createUserDir(user.getId().toString());
        modelAndView.setViewName("registration_complete");
        modelAndView.addObject(USER_REGISTERED_SUCCESS_KEY,
                String.format(USER_REGISTERED_SUCCESS_MSG, user.getEmail()));
        return modelAndView;
    }

    private boolean existing(User user) {
        return userService.findByEmail(user.getEmail()) != null;
    }

    private void selectDefaultUserRoleRadioButton(Model model) {
        User user = new User();
        UserRole userRole = new UserRole();
        userRole.setId(1);
        user.setUserRole(userRole);
        model.addAttribute("user", user);
    }
}
