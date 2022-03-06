package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationRequestDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.model.Status;
import se.kth.iv1201.iv1201recruitmentapp.service.ApplicationService;

import java.util.Locale;
import java.util.Optional;

/**
 * The controller for the application page.
 */
@Controller
@RequestMapping("/recruiter/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * Creates an application request dto for the current
     * application.
     *
     * @return The application request dto.
     */
    @ModelAttribute("applicationRequest")
    public ApplicationRequestDto applicationDto() {
        return new ApplicationRequestDto();
    }

    /**
     * Get request for the recruiter application page.
     * Will display information about the application
     * in the current locale as specified by an
     * application id.
     *
     * @param model Model object used by the application page.
     * @param locale The locale.
     * @param applicationId The application id.
     * @return The recruiter application page.
     */
    @GetMapping()
    public String showApplication(Model model, Locale locale, @RequestParam("applicationId") Optional<Integer> applicationId) {
        if (applicationId.isEmpty())
            return "redirect:/recruiter/applications";

        setStatusOptions(model, locale);

        ApplicationResponseDto response = applicationService.getApplicationData(applicationId.get(), locale);
        model.addAttribute("applicationData", response);

        return "recruiter/application";
    }


    /**
     * TODO
     *
     * @param model
     * @param locale
     * @param applicationId
     * @return
     */
    @PostMapping()
    public String updateApplication(Model model, Locale locale, @RequestParam("applicationId") Optional<Integer> applicationId) {
        if (applicationId.isEmpty())
            return "redirect:/recruiter/applications";

        //setStatusOptions(model, locale);
        return "";
    }

    private void setStatusOptions(Model model, Locale locale) {
        Status[] statusOptions = new Status[3];

        switch (locale.getLanguage()) {
            case "sv":
                statusOptions[0] = new Status(1, "antagen");
                statusOptions[1] = new Status(2, "avslagen");
                statusOptions[2] = new Status(3, "obehandlad");
                break;
            default:
                statusOptions[0] = new Status(1, "accepted");
                statusOptions[1] = new Status(2, "rejected");
                statusOptions[2] = new Status(3, "unhandled");
                break;
        }

        model.addAttribute("statusOptions", statusOptions);
    }

}

