package se.kth.iv1201.iv1201recruitmentapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationsRequestDto;
import se.kth.iv1201.iv1201recruitmentapp.controller.dto.ApplicationsResponseDto;
import se.kth.iv1201.iv1201recruitmentapp.model.Application;
import se.kth.iv1201.iv1201recruitmentapp.service.ApplicationsService;

/**
 * The controller for the applications page.
 */
@Controller
@RequestMapping("/recruiter/applications")
public class ApplicationsController {

    @Autowired
    private ApplicationsService applicationsService;

    /**
     * Creates an applications request dto for the current user.
     *
     * @return The applications request dto.
     */
    @ModelAttribute("applicationsRequest")
    public ApplicationsRequestDto applicationsDto() {
        return new ApplicationsRequestDto();
    }

    /**
     * Get request for the recruiter applications page.
     * Will display the initial page without any search results.
     *
     * @param model Model object used by the applications page.
     * @return The recruiter applications page.
     */
    @GetMapping()
    public String showApplicationSearchForm(Model model) {
        String[] searchOptions = {"time", "competence", "name"};
        model.addAttribute("searchOptions", searchOptions);
        return "/recruiter/applications";
    }

    /**
     * Post request for the recruiter applications search
     * page. Adds the applications search results as a model
     * attribute. Will display the search results on the
     * recruiter applications page if any.
     *
     * @param model Model object used by the applications page.
     * @param applicationsRequestDto The applications request dto.
     * @return The recruiter applications search result page.
     */
    @PostMapping()
    public String showApplicationSearchResults(Model model, @ModelAttribute("applicationsRequest") ApplicationsRequestDto applicationsRequestDto) {
        try {
            // TODO <change>
            String[] searchOptions = {"time", "competence", "name"};
            model.addAttribute("searchOptions", searchOptions);
            // TODO </change>

            model.addAttribute("applicationsResults", applicationsService.getApplicationsSearchResults(applicationsRequestDto));
            return "/recruiter/applications";
        } catch (Exception ex) {
            return "/recruiter/applications";
        }
    }

}
