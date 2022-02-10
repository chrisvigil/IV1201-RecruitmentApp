package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.UserRegistrationDto;
import se.kth.iv1201.iv1201recruitmentapp.service.SecurityUserDetailService;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller for the registration page.
 */
@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private SecurityUserDetailService userService;

    /**
     * Creates a user registration dto for the current user.
     *
     * @return The user registration dto.
     */
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }

    /**
     * Get request for the registration page.
     *
     * @param model Model object used by the registration page.
     * @return The registration page url.
     */
    @GetMapping
    public String showRegistrationForm(Model model){
        return "registration";
    }

    /**
     * Post request for the registration page.
     *
     * @param userDto User DTO object used by the registration page.
     * @return If successful proceed to the login page, otherwise
     *          the registration page is returned.
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto userDto){
        try {
            userService.createUser(userDto);
            return "redirect:login";
        } catch (Exception ex) {
            return "registration";
        }
    }

}
