package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.kth.iv1201.iv1201recruitmentapp.util.RegistrationLoggingEvent;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.UserRegistrationDto;
import se.kth.iv1201.iv1201recruitmentapp.service.SecurityUserDetailService;

import javax.validation.Valid;

/**
 * The controller for the registration page.
 */
@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private SecurityUserDetailService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

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
     * @return The registration page url.
     */
    @GetMapping
    public String showRegistrationForm(){
        return "registration";
    }

    /**
     * Post request for the registration page.
     *
     * @param userDto User form backing object used by the registration page.
     * @param result Contains the userDto validation results
     * @return If successful returns success page, otherwise
     *          the registration page is returned with validation errors.
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result){
        if (result.hasErrors()) {
            return "registration";
        }
        userService.createUser(userDto);
        publisher.publishEvent(new RegistrationLoggingEvent(userDto.getUsername()));
        return "success";
    }

}
