package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationRequestDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.exception.ApplicationNonexistentException;
import se.kth.iv1201.iv1201recruitmentapp.model.StatusWrapper;
import se.kth.iv1201.iv1201recruitmentapp.service.ApplicationService;
import se.kth.iv1201.iv1201recruitmentapp.util.ApplicationChangeLoggingEvent;

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

    @Autowired
    private ApplicationEventPublisher publisher;

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
        setApplicationData(model, locale, applicationId.get());

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

        boolean success = false;
        try {
            success = applicationService.updateApplicationStatus(applicationId.get(), applicationRequestDto);
        }
        catch (ApplicationNonexistentException e) {
            return "redirect:/recruiter/applications?applicationNonexistent";
        }

        setStatusOptions(model, locale);
        setApplicationData(model, locale, applicationId.get());

        if (success) {
            publisher.publishEvent(new ApplicationChangeLoggingEvent(applicationId.get()));
        }
        else {
            model.addAttribute("updateError", true);
        }

        return "recruiter/application";
    }

    private void setApplicationData(Model model, Locale locale, int applicationId) {
        ApplicationResponseDto response = applicationService.getApplicationData(applicationId, locale);
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

        StatusWrapper[] statusWrapperOptions = new StatusWrapper[statusOptionsValue.length];
        for (int i = 0; i < statusWrapperOptions.length; i++) {
            StatusWrapper statusWrapper = new StatusWrapper();

            statusWrapper.setValue(statusOptionsValue[i]);
            statusWrapper.setText(statusOptionsText[i]);

            statusWrapperOptions[i] = statusWrapper;
        }

        model.addAttribute("statusOptions", statusWrapperOptions);
    }

}

