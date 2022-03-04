package se.kth.iv1201.iv1201recruitmentapp.controller.dto;

import org.springframework.data.domain.Page;
import se.kth.iv1201.iv1201recruitmentapp.model.Application;
import se.kth.iv1201.iv1201recruitmentapp.model.Competence;

import java.util.List;

/**
 * The application dto object containing the resulting
 * applications of a search.
 */
/*public class ApplicationsResponseDto {
    private List<Application> applications;

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

}*/
public class ApplicationsResponseDto {
    private Page<Application> applicationPage;

    public Page<Application> getApplicationPage() {
        return applicationPage;
    }

    public void setApplicationPage(Page<Application> applicationPage) {
        this.applicationPage = applicationPage;
    }

}
