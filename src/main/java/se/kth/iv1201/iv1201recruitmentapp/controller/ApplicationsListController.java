package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationsListRequestDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationsListResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.exception.ApplicationsNameSearchFormatException;
import se.kth.iv1201.iv1201recruitmentapp.exception.ApplicationsTimeSearchFormatException;
import se.kth.iv1201.iv1201recruitmentapp.model.Competence;
import se.kth.iv1201.iv1201recruitmentapp.model.SearchOptionWrapper;
import se.kth.iv1201.iv1201recruitmentapp.service.ApplicationsListService;

import java.util.List;
import java.util.Locale;

/**
 * The controller for the applications page.
 */
@Controller
@RequestMapping("/recruiter/applications")
public class ApplicationsListController {

    @Autowired
    private ApplicationsListService applicationsListService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment environment;

    /**
     * Creates an applications request dto for the current user.
     *
     * @return The applications request dto.
     */
    @ModelAttribute("applicationsRequest")
    public ApplicationsListRequestDto applicationsDto() {
        return new ApplicationsListRequestDto();
    }

    /**
     * Get request for the recruiter applications page.
     * Will display the initial page without any search results,
     * does so in the language specified by locale.
     *
     * @param model Model object used by the applications page.
     * @param locale The locale.
     * @return The recruiter applications page.
     */
    @GetMapping()
    public String showApplicationSearchForm(Model model, Locale locale) {
        setSelectOptionsModelAttributes(model, locale);
        setCompetencesModelAttribute(model);

        return "recruiter/applications";
    }

    /**
     * Post request for the recruiter applications search
     * page. Adds the applications search results as a model
     * attribute. Will display the search results on the
     * recruiter applications page if any, in the language
     * specified by locale.
     *
     * @param model Model object used by the applications page.
     * @param locale The locale.
     * @param applicationsListRequestDto The applications request dto.
     * @return The recruiter applications search result page.
     */
    @PostMapping()
    public String showApplicationSearchResults(Model model, Locale locale, @ModelAttribute("applicationsRequest") ApplicationsListRequestDto applicationsListRequestDto) {
        try {
            setSelectOptionsModelAttributes(model, locale);
            setCompetencesModelAttribute(model);

            ApplicationsListResponseDto response = applicationsListService.getApplicationsSearchResults(applicationsListRequestDto);
            model.addAttribute("applicationsResults", response);

            return "recruiter/applications";
        }
        catch (ApplicationsNameSearchFormatException e) {
            return "redirect:/recruiter/applications?nameError";
        }
        catch (ApplicationsTimeSearchFormatException e) {
            return "redirect:/recruiter/applications?timeError";
        }
    }

    private void setSelectOptionsModelAttributes(Model model, Locale locale) {
        Locale def = new Locale(environment.getProperty("default.language"));

        String[] searchOptionsValue = new String[]{
            messageSource.getMessage("recruiter.applications.name", null, def),
            messageSource.getMessage("recruiter.applications.competence", null, def),
            messageSource.getMessage("recruiter.applications.time", null, def)
        };

        String[] searchOptionsText = new String[]{
            messageSource.getMessage("recruiter.applications.name", null, locale),
            messageSource.getMessage("recruiter.applications.competence", null, locale),
            messageSource.getMessage("recruiter.applications.time", null, locale)
        };

        SearchOptionWrapper[] searchOptionWrappers = new SearchOptionWrapper[searchOptionsValue.length];
        for (int i = 0; i < searchOptionWrappers.length; i++) {
            SearchOptionWrapper searchOptionWrapper = new SearchOptionWrapper();

            searchOptionWrapper.setValue(searchOptionsValue[i]);
            searchOptionWrapper.setText(searchOptionsText[i]);

            searchOptionWrappers[i] = searchOptionWrapper;
        }

        model.addAttribute("applicationsSearchOptions", searchOptionWrappers);
    }

    private void setCompetencesModelAttribute(Model model) {
        List<Competence> competences = applicationsListService.getCompetences();
        model.addAttribute("competences", competences);
    }

}
