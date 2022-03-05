package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationRequestDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationResponseDto;
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
     * Will display information about the application.
     *
     * @param model Model object used by the application page.
     * @return The recruiter application page.
     */
    @GetMapping()
    public String showApplication(Model model, Locale locale, @RequestParam("applicationId") Optional<Integer> applicationId) {
        // TODO check that navigation to this page without an id returns one to the applications search page
        if (applicationId.isEmpty())
            return "redirect:/recruiter/applications";

        setStatusOptions(model, locale);

        ApplicationResponseDto response = applicationService.getApplicationData(applicationId.get());
        model.addAttribute("applicationData", response);

        return "recruiter/application";
    }

    // TODO PostMapping

    private void setStatusOptions(Model model, Locale locale) {
        String[] statusOptions;

        switch (locale.getLanguage()) {
            case "sv":
                statusOptions = new String[]{"antagen", "avslagen", "obehandlad"};
                break;
            default:
                statusOptions = new String[]{"accepted", "rejected", "unhandled"};
                break;
        }

        model.addAttribute("statusOptions", statusOptions);
    }

}

