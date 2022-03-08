package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationRequestDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.model.SearchOption;
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

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment environment;

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
        setApplicationData(model, locale, applicationId);

        return "recruiter/application";
    }

    /**
     * Post request for the recruiter application page.
     * Will update the status of an application as
     * requested, then display information about the
     * application in the current locale as specified
     * by an application id.
     *
     * @param model Model object used by the application page.
     * @param locale The locale.
     * @param applicationId The application id.
     * @param applicationRequestDto The application request dto object.
     * @return The recruiter application page.
     */
    @PostMapping()
    public String updateApplication(Model model,
                                    Locale locale,
                                    @RequestParam("applicationId") Optional<Integer> applicationId,
                                    @ModelAttribute("applicationRequest") ApplicationRequestDto applicationRequestDto) {

        if (applicationId.isEmpty())
            return "redirect:/recruiter/applications";

        //String status = parseStatusNameFromId(applicationRequestDto);
        // TODO isPresent?
        boolean success = applicationService.updateApplicationStatus(applicationId.get(), applicationRequestDto);

        if (success) {
            setStatusOptions(model, locale);
            setApplicationData(model, locale, applicationId);

            return "recruiter/application";
        }
        else {
            return "redirect:/recruiter/application?updateError&applicationId=" + applicationId;
        }

    }

    private void setApplicationData(Model model, Locale locale, Optional<Integer> applicationId) {
        ApplicationResponseDto response = applicationService.getApplicationData(applicationId.get(), locale);
        model.addAttribute("applicationData", response);
    }

    private void setStatusOptions(Model model, Locale locale) {
        Locale def = new Locale(environment.getProperty("default.language"));

        String[] statusOptionsValue = new String[]{
                messageSource.getMessage("recruiter.application.option.accepted", null, def),
                messageSource.getMessage("recruiter.application.option.rejected", null, def),
                messageSource.getMessage("recruiter.application.option.unhandled", null, def)
        };

        String[] statusOptionsText = new String[]{
                messageSource.getMessage("recruiter.application.option.accepted", null, locale),
                messageSource.getMessage("recruiter.application.option.rejected", null, locale),
                messageSource.getMessage("recruiter.application.option.unhandled", null, locale)
        };

        Status[] statusOptions = new Status[statusOptionsValue.length];
        for (int i = 0; i < statusOptions.length; i++) {
            Status status = new Status();

            status.setValue(statusOptionsValue[i]);
            status.setText(statusOptionsText[i]);

            statusOptions[i] = status;
        }

        model.addAttribute("statusOptions", statusOptions);
    }

}

