package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The controller for simple Get requests.
 */
@Controller
public class MainController {

    /**
     * Get request for the root page.
     *
     * @return The starting page url.
     */
    @GetMapping("/")
    public String root(){
        return "index";
    }

    /**
     * Get request for the login page.
     *
     * @param model Model object used by the login page.
     * @return The login page url.
     */
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
