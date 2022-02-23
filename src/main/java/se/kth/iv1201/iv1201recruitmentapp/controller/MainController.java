package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The controller for simple Get requests.
 */
@Controller
public class MainController {

    /**
     * Get request for the root page by authority.
     *
     * @return The starting page url.
     */
    @GetMapping("/")
    public String root(Authentication authentication) {
        var authorities =  authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("recruiter"))
                return "redirect:/recruiter/";
        }
        return "redirect:/applicant/";
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

    /**
     * Get request for the applicant index page
     * @param model Model object used by the login page
     * @return The applicants index page
     */
    @GetMapping("/applicant/")
    public String applicantIndex(Model model) {
        return "/applicant/index";
    }

    /**
     * Get request for the recruiters index page
     * @param model Model object used by the login page
     * @return The applicant index page
     */
    @GetMapping("/recruiter/")
    public String recruiterIndex(Model model) {
        return "/recruiter/index";
    }


    //TODO
    @GetMapping("/error/dbConnectionError")
    public String dbError(){ return "/error/dbConnectionError";}
}
