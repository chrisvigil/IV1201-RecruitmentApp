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
 * The controller for the applications page
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
        // TODO populate dropdown for search criteria

        // TODO remove me... please
        model.addAttribute("applicationsResults", null);
        return "/recruiter/applications";
    }

    /**
     * Post request for the recruiter applications search
     * page. Will display the search results if any.
     *
     * @param model Model object used by the applications page.
     * @param applicationsRequestDto The applications request dto.
     * @return The recruiter applications search result page.
     */
    @PostMapping()
    public String showApplicationSearchResults(Model model, @ModelAttribute("applicationsRequest") ApplicationsRequestDto applicationsRequestDto) {
        try {
            // TODO applicationService addAttribute applicationSearchResults
            model.addAttribute("applicationsResults", applicationsService.getApplicationsSearchResults(applicationsRequestDto));
            // <DEBUG>
            ApplicationsResponseDto test1 = (ApplicationsResponseDto) model.getAttribute("applicationsResults");
            java.util.List<Application> test2 = test1.getApplications();
            for (Application t : test2)
                System.out.println(">>> " + t.getPerson().getName() + " " + t.getPerson().getSurname());
            // </DEBUG>
            return "/recruiter/applications";
        } catch (Exception ex) { // TODO error handling?
            return "/recruiter/applications";
        }
    }

}
